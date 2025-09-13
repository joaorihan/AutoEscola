package br.com.fiap3espg.spring_boot_project.controller;

import br.com.fiap3espg.spring_boot_project.agendamento.*;
import br.com.fiap3espg.spring_boot_project.aluno.Aluno;
import br.com.fiap3espg.spring_boot_project.aluno.AlunoRepository;
import br.com.fiap3espg.spring_boot_project.instrutor.Instrutor;
import br.com.fiap3espg.spring_boot_project.instrutor.InstrutorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    AgendamentoInstrucaoRepository agendamentoRepository;
    
    @Autowired
    AlunoRepository alunoRepository;
    
    @Autowired
    InstrutorRepository instrutorRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> agendarInstrucao(@RequestBody @Valid DadosCadastroAgendamento dados) {
        // Validações de negócio
        String validacao = validarAgendamento(dados);
        if (validacao != null) {
            return ResponseEntity.badRequest().body(validacao);
        }

        Aluno aluno = alunoRepository.getReferenceById(dados.alunoId());
        
        // Verificar se aluno está ativo
        if (!aluno.getAtivo()) {
            return ResponseEntity.badRequest().body("Não é possível agendar instrução com aluno inativo");
        }

        Instrutor instrutor = null;
        if (dados.instrutorId() != null) {
            instrutor = instrutorRepository.getReferenceById(dados.instrutorId());
            // Verificar se instrutor está ativo
            if (!instrutor.getAtivo()) {
                return ResponseEntity.badRequest().body("Não é possível agendar instrução com instrutor inativo");
            }
        } else {
            // Escolher instrutor aleatoriamente disponível
            instrutor = escolherInstrutorDisponivel(dados.dataHora());
            if (instrutor == null) {
                return ResponseEntity.badRequest().body("Nenhum instrutor disponível na data/hora solicitada");
            }
        }

        AgendamentoInstrucao agendamento = new AgendamentoInstrucao(dados, aluno, instrutor);
        agendamentoRepository.save(agendamento);
        
        return ResponseEntity.ok("Agendamento realizado com sucesso");
    }

    @GetMapping
    public Page<DadosListagemAgendamento> listarAgendamentos(@PageableDefault(size = 10, sort = {"dataHora"}) Pageable paginacao) {
        return agendamentoRepository.findAll(paginacao).map(DadosListagemAgendamento::new);
    }

    @PostMapping("/cancelar")
    @Transactional
    public ResponseEntity<String> cancelarAgendamento(@RequestBody @Valid DadosCancelamentoAgendamento dados) {
        AgendamentoInstrucao agendamento = agendamentoRepository.getReferenceById(dados.agendamentoId());
        
        // Verificar se pode cancelar (24h de antecedência)
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime limiteCancelamento = agendamento.getDataHora().minusHours(24);
        
        if (agora.isAfter(limiteCancelamento)) {
            return ResponseEntity.badRequest().body("Agendamento só pode ser cancelado com 24h de antecedência");
        }
        
        agendamento.cancelar(dados);
        return ResponseEntity.ok("Agendamento cancelado com sucesso");
    }

    private String validarAgendamento(DadosCadastroAgendamento dados) {
        LocalDateTime dataHora = dados.dataHora();
        
        // Verificar horário de funcionamento (segunda a sábado, 06:00 às 21:00)
        DayOfWeek diaSemana = dataHora.getDayOfWeek();
        if (diaSemana == DayOfWeek.SUNDAY) {
            return "A auto-escola não funciona aos domingos";
        }
        
        LocalTime hora = dataHora.toLocalTime();
        if (hora.isBefore(LocalTime.of(6, 0)) || hora.isAfter(LocalTime.of(21, 0))) {
            return "Horário fora do funcionamento (06:00 às 21:00)";
        }
        
        // Verificar antecedência mínima de 30 minutos
        LocalDateTime agora = LocalDateTime.now();
        if (dataHora.isBefore(agora.plusMinutes(30))) {
            return "Agendamento deve ser feito com antecedência mínima de 30 minutos";
        }
        
        // Verificar se aluno não tem mais de 2 instruções no mesmo dia
        Aluno aluno = alunoRepository.getReferenceById(dados.alunoId());
        long countAgendamentos = agendamentoRepository.countByAlunoAndData(aluno, dataHora);
        if (countAgendamentos >= 2) {
            return "Aluno já possui 2 instruções agendadas neste dia";
        }
        
        return null;
    }

    private Instrutor escolherInstrutorDisponivel(LocalDateTime dataHora) {
        List<Instrutor> instrutoresAtivos = instrutorRepository.findAll().stream()
                .filter(Instrutor::getAtivo)
                .toList();
        
        List<Instrutor> instrutoresDisponiveis = instrutoresAtivos.stream()
                .filter(instrutor -> agendamentoRepository.findByInstrutorAndDataHora(instrutor, dataHora).isEmpty())
                .toList();
        
        if (instrutoresDisponiveis.isEmpty()) {
            return null;
        }
        
        Random random = new Random();
        return instrutoresDisponiveis.get(random.nextInt(instrutoresDisponiveis.size()));
    }
}
