package com.gui9394.rinha_de_backend_2024_q1.extrato;

import java.util.List;

public record Extrato(

        ExtratoSaldo saldo,

        List<ExtratoTransacao> ultimasTransacoes

) {
}
