package com.gui9394.rinha_de_backend_2024_q1.transacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record NovaTransacao(

        @Positive
        Long valor,

        TipoTransacao tipo,

        @Size(min = 1, max = 10)
        @NotBlank
        String descricao

) {
}
