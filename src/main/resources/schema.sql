CREATE TABLE cliente (
	id                           BIGINT                    NOT NULL,
	limite                       BIGINT      DEFAULT 0     NOT NULL,
	saldo                        BIGINT                    NOT NULL,
	CONSTRAINT cliente_pk        PRIMARY KEY (id),
	CONSTRAINT cliente_limite_ck CHECK       ((saldo + limite) > -1)
);

CREATE TABLE transacao (
	id                           BIGINT      GENERATED ALWAYS AS IDENTITY,
	valor                        BIGINT                    NOT NULL,
	tipo                         VARCHAR(10)               NOT NULL,
	descricao                    VARCHAR(10)               NOT NULL,
	realizada_em                 TIMESTAMPTZ DEFAULT NOW() NOT NULL,
	cliente_id                   BIGINT                    NOT NULL,
    CONSTRAINT transacao_pk      PRIMARY KEY (id),
    CONSTRAINT cliente_fk        FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

CREATE INDEX CONCURRENTLY idx_transacao_01 ON transacao (
    cliente_id,
    id         DESC
);

CREATE OR REPLACE PROCEDURE nova_transacao (
	IN    cliente_id              BIGINT,
	IN    transacao_tipo          VARCHAR(10),
	IN    transacao_valor         BIGINT,
	IN    transacao_descricao     VARCHAR(10),
	INOUT resultado               VARCHAR(30),
	INOUT cliente_saldo           BIGINT,
	INOUT cliente_limite          BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE cliente
    SET saldo = (
        CASE transacao_tipo
        WHEN 'DEBIT'  THEN saldo - transacao_valor
        WHEN 'CREDIT' THEN saldo + transacao_valor
        END
    )
    WHERE id = cliente_id
    RETURNING
        saldo,
        limite
    INTO
        cliente_saldo,
        cliente_limite;

	IF NOT FOUND THEN
		resultado := 'CLIENTE_NAO_ENCONTRADO';
		RETURN;
    END IF;

	resultado := 'SUCESSO';
    INSERT INTO transacao (
        tipo,
        valor,
        descricao,
        cliente_id
    )
    VALUES (
        transacao_tipo,
        transacao_valor,
        transacao_descricao,
        cliente_id
    );

    EXCEPTION
        WHEN SQLSTATE '23514' THEN
            resultado := 'CLIENTE_LIMITE_EXCEDIDO';
END;
$$;

INSERT INTO cliente
VALUES
    (1, 100000, 0),
	(2, 80000, 0),
	(3, 1000000, 0),
	(4, 10000000, 0),
	(5, 500000, 0);
