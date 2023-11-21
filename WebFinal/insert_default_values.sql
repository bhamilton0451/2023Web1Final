SET DATABASE SQL SYNTAX PGS TRUE;

INSERT INTO imagem (link) VALUES
('/imagens/error.jpg'), ('/imagens/carne do sol.jpeg'), ('/imagens/cafe com leite.jpeg'),
('/imagens/lasanha.jpeg'), ('/imagens/macarrao.jpeg'), ('/imagens/misto quente.jpeg'),
('/imagens/moqueca.jpeg'), ('/imagens/mousse.jpeg'), ('/imagens/pao artesanal.jpeg'),
('/imagens/peixe frito.jpeg'), ('/imagens/picole.jpeg'), ('/imagens/sorvete.jpeg');

INSERT INTO usuario(login, senha) VALUES ('admin', 'admin');

INSERT INTO prato_tipo(nome) VALUES ('entrada'), ('principal'), ('sobremesa');

INSERT INTO ingrediente(nome) VALUES 
('pao'), ('queijo'), ('presunto'), 
('leite'), ('cafe'), ('macarrao'),
('alho'), ('oleo'), ('camarao'), 
('peixe'), ('carne'), ('gelo'), 
('chocolate');

INSERT INTO prato(nome, tipo) VALUES 
('pao artesanal', 1), ('misto quente', 1), ('cafe com leite', 1),
('lasanha', 2), ('macarrao alho e oleo', 2), ('moqueca de camarao', 2),
('peixe frito', 2), ('carne do sol', 2),
('sorvete', 3), ('picole', 3), ('mousse', 3); 

INSERT INTO prato_ingrediente(prat_id, ing_id) VALUES
(1, 1), (1, 2), (2, 1), (2, 2), (2, 3), (3, 4), (3, 5), (4, 2), (4, 3), (4, 8), (4, 11), (5, 6), (5, 7), (5, 8),
(6, 9), (7, 8), (7, 10), (8, 8), (8, 11), (9, 12), (9, 13), (10, 12), (10, 13), (11, 13);

