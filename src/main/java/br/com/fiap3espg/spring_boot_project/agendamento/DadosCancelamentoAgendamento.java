package br.com.fiap3espg.spring_boot_project.agendamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoAgendamento(

        @NotNull
        Long agendamentoId,
        
        @NotBlank
        String motivoCancelamento) {
}
