package com.gui9394.rinha_de_backend_2024_q1.transacao;

import com.gui9394.rinha_de_backend_2024_q1.cliente.ClienteBalance;
import com.gui9394.rinha_de_backend_2024_q1.cliente.ClienteLimiteExcedidoException;
import com.gui9394.rinha_de_backend_2024_q1.cliente.ClienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public Mono<ClienteBalance> novaTransacao(
            final Long clientId,
            final NovaTransacao novaTransacao
    ) {
        return transacaoRepository.novaTransacao(
                        clientId,
                        novaTransacao
                )
                .onErrorMap(this::errorMapper)
                .doOnError(error -> log.error(
                        "clientId={} transacaoValor={} transacaoTipo={} transacaoDescricao={}",
                        clientId,
                        novaTransacao.valor(),
                        novaTransacao.tipo(),
                        novaTransacao.descricao(),
                        error
                ))
                .doOnNext(clientSaldo -> log.info(
                        "clientId={} clientSaldo={} clienteLimite={} transacaoValor={} transacaoTipo={} transacaoDescricao={}",
                        clientId,
                        clientSaldo.saldo(),
                        clientSaldo.limite(),
                        novaTransacao.valor(),
                        novaTransacao.tipo(),
                        novaTransacao.descricao()
                ));
    }

    private Throwable errorMapper(Throwable throwable) {
        if (Objects.isNull(throwable.getCause())) {
            return throwable;
        }

        return switch (throwable.getCause().getMessage()) {
            case "cliente_nao_encontrado" -> new ClienteNaoEncontradoException();
            case "cliente_limite_excedido" -> new ClienteLimiteExcedidoException();
            default -> throwable;
        };
    }

}
