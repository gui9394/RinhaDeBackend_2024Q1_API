package com.gui9394.rinha_de_backend_2024_q1.cliente.extrato;

import com.gui9394.rinha_de_backend_2024_q1.cliente.transacao.TransacaoTipo;

import java.time.Instant;

public record ExtratoTransacao(

        Long valor,

        TransacaoTipo tipo,

        String descricao,

        Instant realizadaEm

) {
}