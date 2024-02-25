package com.gui9394.rinha_de_backend_2024_q1.transacao;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TipoTransacao {

    @JsonProperty("c")
    CREDIT,

    @JsonProperty("d")
    DEBIT;

}
