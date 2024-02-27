package com.gui9394.rinha_de_backend_2024_q1.cliente.transacao;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransacaoTipo {

    @JsonProperty("c")
    CREDIT,

    @JsonProperty("d")
    DEBIT;

}
