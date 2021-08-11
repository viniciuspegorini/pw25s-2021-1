
insert into categoria (nome) values ('Informática');
insert into categoria (nome) values ('UD');
insert into categoria (nome) values ('Cozinha');
insert into categoria (nome) values ('Móveis');

insert into marca (nome) values ('GL');
insert into marca (nome) values ('Reizer');
insert into marca (nome) values ('Ynos');

insert into produto (nome, descricao, valor, categoria_id, marca_id) values ('Refrigerador 429L','Refrigerador 429L Branco, duplex....',1990.0,2,1);
insert into produto (nome, descricao, valor, categoria_id, marca_id) values ('Notebook Arus 15.6','Notebook Arus 15.6 Core I7, 16Gb Ram...',2449.0,1,3);
insert into produto (nome, descricao, valor, categoria_id, marca_id) values ('Monitor 27pol','Monitor Gamer 27pol 144Hz, 1ms',1129.99,1,2);
insert into produto (nome, descricao, valor, categoria_id, marca_id) values ('Kit Teclado e Mouse','Kit com teclado ABNT e mouse com 5 botões',199.0,1,1);

INSERT INTO permissao (nome) values('ROLE_ADMIN');
INSERT INTO permissao (nome) values('ROLE_USER');

INSERT INTO usuario(nome, username, password) VALUES ('Administrador', 'admin','$2a$10$.PVIfB07x.SfMYTcToxL0.yxcLWU0GbS2NUO1W1QAvqMm/TsFhVem');
INSERT INTO usuario(nome, username, password) VALUES ('Teste', 'teste','$2a$10$.PVIfB07x.SfMYTcToxL0.yxcLWU0GbS2NUO1W1QAvqMm/TsFhVem');

INSERT INTO usuario_permissoes(usuario_id, permissoes_id) VALUES (1, 1);
INSERT INTO usuario_permissoes(usuario_id, permissoes_id) VALUES (1, 2);
INSERT INTO usuario_permissoes(usuario_id, permissoes_id) VALUES (2, 2);

insert into fornecedor (nome, cnpj) values ('Fornecedor 1', '73.661.932/0001-59');
insert into fornecedor (nome, cnpj) values ('Fornecedor 2', '20.148.302/0001-19');
insert into fornecedor (nome, cnpj) values ('Fornecedor 3', '15.481.327/0001-08');