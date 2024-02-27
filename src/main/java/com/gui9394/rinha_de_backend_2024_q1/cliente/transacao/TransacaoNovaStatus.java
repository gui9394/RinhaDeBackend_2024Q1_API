package com.gui9394.rinha_de_backend_2024_q1.cliente.transacao;

import com.gui9394.rinha_de_backend_2024_q1.cliente.ClienteLimiteExcedidoException;
import com.gui9394.rinha_de_backend_2024_q1.cliente.ClienteNaoEncontradoException;

public enum TransacaoNovaStatus {

    SUCESSO {

        @Override
        public void process(
                Long clienteId,
                TransacaoNova transacaoNova,
                TransacaoNovaResultado transacaoNovaResultado
        ) {
        }

    },

    CLIENTE_NAO_ENCONTRADO {

        @Override
        public void process(
                Long clienteId,
                TransacaoNova transacaoNova,
                TransacaoNovaResultado transacaoNovaResultado
        ) {
            throw new ClienteNaoEncontradoException(clienteId);
        }

    },

    CLIENTE_LIMITE_EXCEDIDO {

        @Override
        public void process(
                Long clienteId,
                TransacaoNova transacaoNova,
                TransacaoNovaResultado transacaoNovaResultado
        ) {
            throw new ClienteLimiteExcedidoException(
                    clienteId,
                    transacaoNovaResultado.limite(),
                    transacaoNovaResultado.saldo()
            );
        }

    };

    public abstract void process(
            Long clienteId,
            TransacaoNova transacaoNova,
            TransacaoNovaResultado transacaoNovaResultado
    );

}
