INSERT IGNORE INTO cozinha (id,nome) VALUES (1,'Tailandesa');
INSERT IGNORE INTO cozinha (id,nome) VALUES (2,'Indiana');
INSERT IGNORE INTO cozinha (id,nome) VALUES (3,'Brasileira');

INSERT IGNORE INTO forma_pagamento(id,descricao) VALUES(1,'Crédito');
INSERT IGNORE INTO forma_pagamento(id,descricao) VALUES(2,'Débito');
INSERT IGNORE INTO forma_pagamento(id,descricao) VALUES(3,'Dinheiro');
INSERT IGNORE INTO forma_pagamento(id,descricao) VALUES(4,'Ticket');

INSERT IGNORE INTO permissao (id,nome,descricao) VALUES(1,'Consultar Pedidos','Permissão de consultar histórico de pedidos');
INSERT IGNORE INTO permissao (id,nome,descricao) VALUES(2,'Consultar Restaurantes','Permissão de consultar restaurantes');

INSERT IGNORE INTO estado (id,nome) VALUES (1,'Acre');
INSERT IGNORE INTO estado (id,nome) VALUES (2,'Alagoas');
INSERT IGNORE INTO estado (id,nome) VALUES (3,'Amazonas');
INSERT IGNORE INTO estado (id,nome) VALUES (4,'Amapá');
INSERT IGNORE INTO estado (id,nome) VALUES (5,'Bahia');
INSERT IGNORE INTO estado (id,nome) VALUES (6,'Ceará');
INSERT IGNORE INTO estado (id,nome) VALUES (7,'Distrito Federal');
INSERT IGNORE INTO estado (id,nome) VALUES (8,'Espírito Santo');
INSERT IGNORE INTO estado (id,nome) VALUES (9,'Goiás');
INSERT IGNORE INTO estado (id,nome) VALUES (10,'Maranhão');
INSERT IGNORE INTO estado (id,nome) VALUES (11,'Minas Gerais');
INSERT IGNORE INTO estado (id,nome) VALUES (12,'Mato Grosso do Sul');
INSERT IGNORE INTO estado (id,nome) VALUES (13,'Mato Grosso');
INSERT IGNORE INTO estado (id,nome) VALUES (14,'Pará');
INSERT IGNORE INTO estado (id,nome) VALUES (15,'Paraíba');
INSERT IGNORE INTO estado (id,nome) VALUES (16,'Paraná');
INSERT IGNORE INTO estado (id,nome) VALUES (17,'Pernambuco');
INSERT IGNORE INTO estado (id,nome) VALUES (18,'Piauí');
INSERT IGNORE INTO estado (id,nome) VALUES (19,'Rio de Janeiro');
INSERT IGNORE INTO estado (id,nome) VALUES (20,'Rio Grande do Norte');
INSERT IGNORE INTO estado (id,nome) VALUES (21,'Rio Grande do Sul');
INSERT IGNORE INTO estado (id,nome) VALUES (22,'Rondônia');
INSERT IGNORE INTO estado (id,nome) VALUES (23,'Roraima');
INSERT IGNORE INTO estado (id,nome) VALUES (24,'Santa Catarina');
INSERT IGNORE INTO estado (id,nome) VALUES (25,'São Paulo');
INSERT IGNORE INTO estado (id,nome) VALUES (26,'Sergipe');
INSERT IGNORE INTO estado (id,nome) VALUES (27,'Tocantins');

INSERT IGNORE INTO cidade (id,nome,estado_id) VALUES (1,'Recife',17);
INSERT IGNORE INTO cidade (id,nome,estado_id) VALUES (2,'Campina Grande',15);

INSERT IGNORE INTO restaurante (id,endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, nome, taxa_frete, id_cozinha, endereco_cidade_id,data_cadastro,data_atualizacao) VALUES (1,'Centro','55700000','','av capibaribe','581','Portal do Derby',6.90,1,1,utc_timestamp,utc_timestamp);
INSERT IGNORE INTO restaurante (id,endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, nome, taxa_frete, id_cozinha, endereco_cidade_id,data_cadastro,data_atualizacao) VALUES (2,'Casa Forte','51700030','','av vitoria regia','512','Taysunan',7.90,2,2,utc_timestamp,utc_timestamp);
INSERT IGNORE INTO restaurante (id,endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, nome, taxa_frete, id_cozinha, endereco_cidade_id,data_cadastro,data_atualizacao) VALUES (3,'Caxangá','51021060','','av simoes barbosa','1122','Capiba Bar',4.90,1,1,utc_timestamp,utc_timestamp);

INSERT IGNORE INTO restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (1,1),(1,2),(1,3),(2,1),(2,2),(3,1);

INSERT IGNORE INTO produto (id,ativo,descricao,nome,preco,id_restaurante) VALUES(1,true,'macarrão frito','shop suey',23.90,1);

INSERT IGNORE INTO produto (id,ativo,descricao,nome,preco,id_restaurante) VALUES(2,true,'churrasco de primeira','picanha argentina',49.90,2);
