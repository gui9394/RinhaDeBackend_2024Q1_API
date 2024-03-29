package com.gui9394.rinha_de_backend_2024_q1.cliente.extrato;

import com.gui9394.rinha_de_backend_2024_q1.cliente.transacao.TransacaoTipo;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExtratoRepository {

    private static final String EXTRATO_SALDO_QUERY = """
            SELECT
                saldo,
                limite,
                now() as data_extrato
            FROM cliente
            WHERE id = :clienteId
            """;

    private static final String EXTRATO_TRANSACAO_QUERY = """
            SELECT
                valor,
                tipo,
                descricao,
                realizada_em
            FROM transacao
            WHERE cliente_id = :clienteId
            ORDER BY id DESC
            LIMIT 10
            """;

    private final DatabaseClient databaseClient;

    public Mono<ExtratoSaldo> findSaldoByClienteId(Long clienteId) {
        return databaseClient.sql(EXTRATO_SALDO_QUERY)
                .bind("clienteId", clienteId)
                .fetch()
                .first()
                .filter(values -> !values.isEmpty())
                .map(values -> new ExtratoSaldo(
                        (Long) values.get("saldo"),
                        (Long) values.get("limite"),
                        ((OffsetDateTime) values.get("data_extrato")).toInstant()
                ));
    }

    public Mono<List<ExtratoTransacao>> findTransacoesByClienteId(Long clienteId) {
        return databaseClient.sql(EXTRATO_TRANSACAO_QUERY)
                .bind("clienteId", clienteId)
                .fetch()
                .all()
                .map(resultado -> new ExtratoTransacao(
                        (Long) resultado.get("valor"),
                        TransacaoTipo.valueOf((String) resultado.get("tipo")),
                        (String) resultado.get("descricao"),
                        ((OffsetDateTime) resultado.get("realizada_em")).toInstant()
                ))
                .collectList();
    }

}
