package com.gui9394.rinha_de_backend_2024_q1.cliente.transacao;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class TransacaoRepository {

    private static final String NOVA_TRANSACAO_QUERY = """
            call nova_transacao(
                    :clienteId,
                    :transacaoTipo,
                    :transacaoValor,
                    :transacaoDescricao,
                    NULL,
                    NULL,
                    NULL
            )
            """;

    private final DatabaseClient databaseClient;

    public Mono<TransacaoNovaResultado> nova(Long clienteId, TransacaoNova transacaoNova) {
        return databaseClient.sql(NOVA_TRANSACAO_QUERY)
                .bind("clienteId", clienteId)
                .bind("transacaoTipo", transacaoNova.tipo().name())
                .bind("transacaoValor", transacaoNova.valor())
                .bind("transacaoDescricao", transacaoNova.descricao())
                .fetch()
                .first()
                .map(resultado -> new TransacaoNovaResultado(
                        TransacaoNovaStatus.valueOf((String) resultado.get("resultado")),
                        (Long) resultado.get("cliente_limite"),
                        (Long) resultado.get("cliente_saldo")
                ));
    }

}
