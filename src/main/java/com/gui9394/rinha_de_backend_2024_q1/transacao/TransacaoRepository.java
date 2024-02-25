package com.gui9394.rinha_de_backend_2024_q1.transacao;

import com.gui9394.rinha_de_backend_2024_q1.cliente.ClienteBalance;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Map;

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
                    NULL
            )
            """;

    private final DatabaseClient databaseClient;

    public Mono<ClienteBalance> novaTransacao(Long clienteId, NovaTransacao novaTransacao) {
        return databaseClient.sql(NOVA_TRANSACAO_QUERY)
                .bind("clienteId", clienteId)
                .bind("transacaoTipo", novaTransacao.tipo().name())
                .bind("transacaoValor", novaTransacao.valor())
                .bind("transacaoDescricao", novaTransacao.descricao())
                .fetch()
                .first()
                .map(this::mapResult);
    }

    private ClienteBalance mapResult(Map<String, Object> result) {
        return new ClienteBalance(
                (Long) result.get("cliente_limite"),
                (Long) result.get("cliente_saldo")
        );
    }

}
