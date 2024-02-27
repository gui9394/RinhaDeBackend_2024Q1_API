package com.gui9394.rinha_de_backend_2024_q1.cliente.extrato;

import com.gui9394.rinha_de_backend_2024_q1.cliente.ClienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExtratoService {

    private final ExtratoRepository extratoRepository;

    public Mono<Extrato> extrato(Long clientId) {
        return Mono.zip(
                        extratoRepository.findSaldoByClienteId(clientId)
                                .switchIfEmpty(Mono.error(() -> new ClienteNaoEncontradoException(clientId))),
                        extratoRepository.findTransacoesByClienteId(clientId)
                )
                .map(tuple -> new Extrato(tuple.getT1(), tuple.getT2()))
                .doOnError(erro -> log.error("clientId={}", clientId, erro))
                .doOnNext(extrato -> log.info(
                        "clienteId={} clienteSaldo={} clienteLimite={}",
                        clientId,
                        extrato.saldo().total(),
                        extrato.saldo().limite()
                ));
    }

}
