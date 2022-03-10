INSERT INTO cozinha(nome) VALUES ('Tailandesa');
INSERT INTO cozinha(nome) VALUES ('Indiana');

INSERT INTO restaurante(nome, taxa_frete, cozinha_id) VALUES ('Thai Gourmet',10,1)
INSERT INTO restaurante(nome, taxa_frete, cozinha_id) VALUES ('Thai Delivery',9.50,1)
INSERT INTO restaurante(nome, taxa_frete, cozinha_id) VALUES ('Tuk tuk Comida Indiana',15,2)

INSERT INTO forma_pagamento(descricao) VALUES ('A vista')
INSERT INTO forma_pagamento(descricao) VALUES ('Cartão Visa')
INSERT INTO forma_pagamento(descricao) VALUES ('Cartão Master')

INSERT INTO permissao(nome, descricao) VALUES ('Consultar',' Permite fazer consultas')
INSERT INTO permissao(nome, descricao) VALUES ('Alterar',' Permite alterar dados')

INSERT INTO estado(nome) VALUES ('MG')
INSERT INTO estado(nome) VALUES ('ES')
INSERT INTO estado(nome) VALUES ('SP')
INSERT INTO estado(nome) VALUES ('RJ')

INSERT INTO cidade(nome) VALUES ('Belo Horizonte')
INSERT INTO cidade(nome) VALUES ('Vila Velha')
INSERT INTO cidade(nome) VALUES ('Vitória')
INSERT INTO cidade(nome) VALUES ('Santos')