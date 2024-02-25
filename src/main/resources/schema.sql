CREATE TABLE IF NOT EXISTS client(
	client_id        BIGINT      GENERATED ALWAYS AS IDENTITY,
	client_limit     BIGINT      NOT NULL,
	client_balance   BIGINT      NOT NULL,
	PRIMARY KEY (client_id)
);

CREATE TABLE IF NOT EXISTS transaction(
	transaction_id               BIGINT         GENERATED ALWAYS AS IDENTITY,
	transaction_value            BIGINT         NOT NULL,
	transaction_type             VARCHAR(10)    NOT NULL,
	transaction_description      VARCHAR(10)    NOT NULL,
	transaction_created_at       TIMESTAMPTZ    DEFAULT NOW() NOT NULL,
	client_id                    BIGINT         NOT NULL,
    PRIMARY KEY (transaction_id),
    CONSTRAINT FK_CLIENT FOREIGN KEY (client_id) REFERENCES client(client_id)
);

CREATE OR REPLACE PROCEDURE save_transaction(
	client_id BIGINT,
	transaction_type VARCHAR(10),
	transaction_value BIGINT,
	transaction_description VARCHAR(10),
	INOUT client_balance BIGINT,
	INOUT client_limit BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
	SELECT
		c.client_balance,
		c.client_limit
	INTO
		client_balance,
		client_limit
	FROM client AS c
	WHERE c.client_id = client_id
	FOR UPDATE;

	IF NOT FOUND THEN
		RAISE EXCEPTION 'client_not_found';
	end IF;

	IF transaction_type = 'DEBIT' THEN
		client_balance = client_balance - transaction_value;

		IF (client_balance * -1) > client_limit THEN
			RAISE EXCEPTION 'client_limit_not_available';
		end IF;
	ELSIF transaction_type = 'CREDIT' THEN
		client_balance = client_balance + transaction_value;
	ELSE
		RAISE EXCEPTION 'transaction_type_invalid';
	END IF;

	UPDATE client AS c
    SET balance = client_balance
    WHERE c.client_id = client_id;

    INSERT INTO transaction (
		transaction_type,
		transaction_value,
		transaction_description,
    	client_id
	)
	VALUES (
		transaction_type,
		transaction_value,
		transaction_description,
		client_id
	);
end;
$$;