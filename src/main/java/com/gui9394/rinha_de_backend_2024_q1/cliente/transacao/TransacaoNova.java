package com.gui9394.rinha_de_backend_2024_q1.cliente.transacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TransacaoNova(

        @Positive
        Long valor,

        @NotNull
        TransacaoTipo tipo,

        @Size(min = 1, max = 10)
        @NotBlank
        String descricao

) {
}
