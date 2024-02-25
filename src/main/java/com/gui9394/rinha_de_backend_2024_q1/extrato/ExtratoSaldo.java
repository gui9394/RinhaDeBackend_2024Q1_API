package com.gui9394.rinha_de_backend_2024_q1.extrato;

import java.time.Instant;

public record ExtratoSaldo(

        Long total,

        Long limite,

        Instant dataExtrato

) {
}
