SET DATABASE SQL SYNTAX PGS TRUE;

DROP TABLE pedido_prato IF EXISTS;
DROP TABLE pedido IF EXISTS;
DROP TABLE prato_ingrediente IF EXISTS;
DROP TABLE prato IF EXISTS;
DROP TABLE ingrediente IF EXISTS;
DROP TABLE prato_tipo IF EXISTS;
DROP TABLE usuario IF EXISTS;
DROP TABLE imagem IF EXISTS;


CREATE TABLE IF NOT EXISTS imagem (
	img_id SERIAL PRIMARY KEY NOT NULL,
	link VARCHAR(250)
);


CREATE TABLE IF NOT EXISTS usuario(
	usr_id SERIAL PRIMARY KEY NOT NULL,
	login VARCHAR(100) NOT NULL,
	senha VARCHAR(100) NOT NULL
);


CREATE TABLE IF NOT EXISTS prato_tipo(
	tp_id SERIAL PRIMARY KEY NOT NULL,
	nome VARCHAR(15) NOT NULL
);


CREATE TABLE ingrediente(
	ing_id SERIAL PRIMARY KEY NOT NULL,
	nome VARCHAR(20),

	img_id INT,
	FOREIGN KEY(img_id) REFERENCES imagem(img_id)
);


CREATE TABLE IF NOT EXISTS prato(
	prat_id SERIAL PRIMARY KEY NOT NULL,
	nome VARCHAR(25) NOT NULL,
	tipo INT NOT NULL,
	img_id INT,

	FOREIGN KEY(img_id) REFERENCES imagem(img_id),
	FOREIGN KEY (tipo) REFERENCES prato_tipo(tp_id)
);

CREATE TABLE prato_ingrediente(
	prat_id INT NOT NULL,
	ing_id INT NOT NULL,

	FOREIGN KEY (prat_id) REFERENCES prato(prat_id),
	FOREIGN KEY (ing_id) REFERENCES ingrediente(ing_id)
);

CREATE TABLE IF NOT EXISTS pedido(
	pd_id SERIAL PRIMARY KEY NOT NULL,
	pd_data DATE NOT NULL DEFAULT CURRENT_DATE,
);

CREATE TABLE IF NOT EXISTS pedido_prato(
	pd_id INT NOT NULL,
	prat_id INT NOT NULL,

	FOREIGN KEY (pd_id) REFERENCES pedido(pd_id),
	FOREIGN KEY (prat_id) REFERENCES prato(prat_id)
);

