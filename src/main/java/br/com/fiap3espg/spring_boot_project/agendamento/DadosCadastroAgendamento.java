package br.com.fiap3espg.spring_boot_project.agendamento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosCadastroAgendamento(

        @NotNull
        Long alunoId,
        
        Long instrutorId, // Opcional - se não informado, sistema escolhe aleatoriamente
        
        @NotNull
        @Future
        LocalDateTime dataHora) {
}
