package com.gui9394.rinha_de_backend_2024_q1.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;

@ControllerAdvice
public class ErroHandler {

    @ExceptionHandler
    public void handler(ServerWebInputException exception) {
        throw new ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                null,
                exception
        );
    }

}
