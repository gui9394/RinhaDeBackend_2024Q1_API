package com.gui9394.rinha_de_backend_2024_q1.cliente;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClienteLimiteExcedidoException extends ResponseStatusException {

    public ClienteLimiteExcedidoException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "Limite superior ao disponivel");
    }

}
