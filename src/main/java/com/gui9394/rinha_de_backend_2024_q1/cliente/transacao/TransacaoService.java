package com.gui9394.rinha_de_backend_2024_q1.cliente.transacao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public Mono<TransacaoNovaResultado> nova(
            final Long clientId,
            final TransacaoNova transacaoNova
    ) {
        return transacaoRepository.nova(
                        clientId,
                        transacaoNova
                )
                .doOnNext(transacaoNovaResultado -> transacaoNovaResultado.resultado().process(
                        clientId,
                        transacaoNova,
                        transacaoNovaResultado
                ))
                .doOnError(erro -> log.error(
                        "clientId={} transacaoValor={} transacaoTipo={} transacaoDescricao={}",
                        clientId,
                        transacaoNova.valor(),
                        transacaoNova.tipo(),
                        transacaoNova.descricao(),
                        erro
                ))
                .doOnNext(transacaoNovaResultado -> log.info(
                        "clientId={} clientSaldo={} clienteLimite={} transacaoValor={} transacaoTipo={} transacaoDescricao={}",
                        clientId,
                        transacaoNovaResultado.saldo(),
                        transacaoNovaResultado.limite(),
                        transacaoNova.valor(),
                        transacaoNova.tipo(),
                        transacaoNova.descricao()
                ));
    }

}
