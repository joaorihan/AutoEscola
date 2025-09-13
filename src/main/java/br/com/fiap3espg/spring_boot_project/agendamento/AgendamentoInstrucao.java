package br.com.fiap3espg.spring_boot_project.agendamento;

import br.com.fiap3espg.spring_boot_project.aluno.Aluno;
import br.com.fiap3espg.spring_boot_project.instrutor.Instrutor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "agendamentos_instrucao")
@Entity(name = "AgendamentoInstrucao")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class AgendamentoInstrucao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id")
    Aluno aluno;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrutor_id")
    Instrutor instrutor;
    
    LocalDateTime dataHora;
    
    @Enumerated(EnumType.STRING)
    StatusAgendamento status = StatusAgendamento.AGENDADO;
    
    String motivoCancelamento;

    public AgendamentoInstrucao(DadosCadastroAgendamento dados, Aluno aluno, Instrutor instrutor) {
        this.aluno = aluno;
        this.instrutor = instrutor;
        this.dataHora = dados.dataHora();
    }

    public Long getId() {
        return id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void cancelar(DadosCancelamentoAgendamento dados) {
        this.status = StatusAgendamento.CANCELADO;
        this.motivoCancelamento = dados.motivoCancelamento();
    }
}
