package com.gui9394.rinha_de_backend_2024_q1.cliente;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClienteNaoEncontradoException extends ResponseStatusException {

    public ClienteNaoEncontradoException() {
        super(HttpStatus.NOT_FOUND, "Cliente não encontrado");
    }

}
