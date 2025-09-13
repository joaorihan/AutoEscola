package br.com.fiap3espg.spring_boot_project.agendamento;

import java.time.LocalDateTime;

public record DadosListagemAgendamento(
        Long id,
        String nomeAluno,
        String nomeInstrutor,
        LocalDateTime dataHora,
        StatusAgendamento status) {

    public DadosListagemAgendamento(AgendamentoInstrucao agendamento) {
        this(agendamento.getId(), 
                agendamento.getAluno().getNome(),
                agendamento.getInstrutor() != null ? agendamento.getInstrutor().getNome() : "A definir",
                agendamento.getDataHora(),
                agendamento.getStatus());
    }
}
