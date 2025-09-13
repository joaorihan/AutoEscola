package br.com.fiap3espg.spring_boot_project.instrutor;

import br.com.fiap3espg.spring_boot_project.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoInstrutor(

        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}