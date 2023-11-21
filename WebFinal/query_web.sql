SET DATABASE SQL SYNTAX PGS TRUE;

/*
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

INSERT INTO imagem (link) VALUES
('/imagens/error.jpg'), ('/imagens/cafe com leite.jpeg'), ('/imagens/carne do sol.jpeg'),
('/imagens/lasanha.jpeg'), ('/imagens/macarrao.jpeg'), ('/imagens/misto quente.jpeg'),
('/imagens/moqueca.jpeg'), ('/imagens/mousse.jpeg'), ('/imagens/pao artesanal.jpeg'),
('/imagens/peixe frito.jpeg'), ('/imagens/picole.jpeg'), ('/imagens/sorvete.jpeg');

CREATE TABLE IF NOT EXISTS usuario(
	usr_id SERIAL PRIMARY KEY NOT NULL,
	login VARCHAR(100) NOT NULL,
	senha VARCHAR(100) NOT NULL
);


INSERT INTO usuario(login, senha) VALUES ('admin', 'admin');


CREATE TABLE IF NOT EXISTS prato_tipo(
	tp_id SERIAL PRIMARY KEY NOT NULL,
	nome VARCHAR(15) NOT NULL
);


INSERT INTO prato_tipo(nome) VALUES ('entrada'), ('principal'), ('sobremesa');


CREATE TABLE ingrediente(
	ing_id SERIAL PRIMARY KEY NOT NULL,
	nome VARCHAR(40),

	img_id INT,
	FOREIGN KEY(img_id) REFERENCES imagem(img_id)
);


INSERT INTO ingrediente(nome) VALUES 
('pao'), ('queijo'), ('presunto'), 
('leite'), ('cafe'), ('macarrao'),
('alho'), ('oleo'), ('camarao'), 
('peixe'), ('carne'), ('gelo'), 
('chocolate');


CREATE TABLE IF NOT EXISTS prato(
	prat_id SERIAL PRIMARY KEY NOT NULL,
	nome VARCHAR(25) NOT NULL,
	tipo INT NOT NULL,
	img_id INT,

	FOREIGN KEY(img_id) REFERENCES imagem(img_id),
	FOREIGN KEY (tipo) REFERENCES prato_tipo(tp_id)
);


INSERT INTO prato(nome, tipo) VALUES 
('pao artesanal', 1), ('misto quente', 1), ('cafe com leite', 1),
('lasanha', 2), ('macarrao alho e oleo', 2), ('moqueca de camarao', 2),
('peixe frito', 2), ('carne do sol', 2),
('sorvete', 3), ('picole', 3), ('mousse', 3); 


CREATE TABLE prato_ingrediente(
	prat_id INT NOT NULL,
	ing_id INT NOT NULL,

	FOREIGN KEY (prat_id) REFERENCES prato(prat_id),
	FOREIGN KEY (ing_id) REFERENCES ingrediente(ing_id)
);


INSERT INTO prato_ingrediente(prat_id, ing_id) VALUES
(1, 1), (1, 2), (2, 1), (2, 2), (2, 3), (3, 4), (3, 5), (4, 2), (4, 3), (4, 8), (4, 11), (5, 6), (5, 7), (5, 8),
(6, 9), (7, 8), (7, 10), (8, 8), (8, 11), (9, 12), (9, 13), (10, 12), (10, 13), (11, 13);

CREATE TABLE IF NOT EXISTS pedido(
	pd_id SERIAL PRIMARY KEY NOT NULL,
	pd_data DATE NOT NULL DEFAULT CURRENT_DATE,
);

CREATE TABLE IF NOT EXISTS pedido_prato(
	pd_id INT NOT NULL,
	prat_id INT NOT NULL,

	FOREIGN KEY (pd_id) REFERENCES pedido(pd_id),
	FOREIGN KEY (prat_id) REFERENCES(pd_id)
);
*/

SELECT MAX(pd_id) FROM pedido;
