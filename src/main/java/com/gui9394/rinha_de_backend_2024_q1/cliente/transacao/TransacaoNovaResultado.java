package com.gui9394.rinha_de_backend_2024_q1.cliente.transacao;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record TransacaoNovaResultado(

        @JsonIgnore
        TransacaoNovaStatus resultado,

        Long limite,

        Long saldo

) {
}
