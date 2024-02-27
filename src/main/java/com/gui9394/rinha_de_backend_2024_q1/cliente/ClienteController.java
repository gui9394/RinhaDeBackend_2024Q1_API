package com.gui9394.rinha_de_backend_2024_q1.cliente;

import com.gui9394.rinha_de_backend_2024_q1.cliente.extrato.Extrato;
import com.gui9394.rinha_de_backend_2024_q1.cliente.extrato.ExtratoService;
import com.gui9394.rinha_de_backend_2024_q1.cliente.transacao.TransacaoNovaResultado;
import com.gui9394.rinha_de_backend_2024_q1.cliente.transacao.TransacaoNova;
import com.gui9394.rinha_de_backend_2024_q1.cliente.transacao.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping(path = "clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ExtratoService extratoService;
    private final TransacaoService transacaoService;

    @GetMapping(path = "{clienteId}/extrato")
    public Mono<Extrato> getExtrato(

            @PathVariable
            Long clienteId

    ) {
        return extratoService.extrato(clienteId);
    }

    @PostMapping(path = "/{clienteId}/transacoes")
    public Mono<TransacaoNovaResultado> newTransaction(

            @PathVariable
            Long clienteId,

            @Valid
            @RequestBody
            TransacaoNova transacaoNova

    ) {
        return transacaoService.nova(clienteId, transacaoNova);
    }

}
