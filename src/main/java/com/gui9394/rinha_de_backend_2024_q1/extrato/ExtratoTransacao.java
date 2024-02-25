package com.gui9394.rinha_de_backend_2024_q1.extrato;

import com.gui9394.rinha_de_backend_2024_q1.transacao.TipoTransacao;

import java.time.Instant;

public record ExtratoTransacao(

        Long valor,

        TipoTransacao tipo,

        String descricao,

        Instant realizadaEm

) {
}