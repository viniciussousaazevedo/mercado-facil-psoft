insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, IS_DISPONIVEL, CATEGORIA, PRECO, IS_REFRIGERADO, IS_FRAGIL)
values(10001,'Leite Integral', '87654321-B', 'Parmalat', FALSE, 'Mercearia', 4.5, FALSE, FALSE);

insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, IS_DISPONIVEL, CATEGORIA, PRECO, IS_REFRIGERADO, IS_FRAGIL)
values(10002,'Arroz Integral', '87654322-B', 'Tio Joao', FALSE, 'Perecíveis', 5.5, FALSE, FALSE);

insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, IS_DISPONIVEL, CATEGORIA, PRECO, IS_REFRIGERADO, IS_FRAGIL)
values(10003,'Sabao em Po', '87654323-B', 'OMO', FALSE, 'Limpeza', 12, FALSE, FALSE);

insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, IS_DISPONIVEL, CATEGORIA, PRECO, IS_REFRIGERADO, IS_FRAGIL)
values(10004,'Agua Sanitaria', '87654324-C', 'Dragao', FALSE, 'limpesa', 3, FALSE, FALSE);

insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, IS_DISPONIVEL, CATEGORIA, PRECO, IS_REFRIGERADO, IS_FRAGIL)
values(10005,'Creme Dental', '87654325-C', 'Colgate', FALSE, 'HIGIENE', 2.5, FALSE, TRUE);

insert into lote (ID, PRODUTO_ID, NUMERO_DE_ITENS)
values(1, 10005, 5);

insert into lote (ID, PRODUTO_ID, NUMERO_DE_ITENS)
values(2, 10001, 5);

update produto set IS_DISPONIVEL = TRUE where ID = 10005;

update produto set IS_DISPONIVEL = TRUE where ID = 10001;

insert into carrinho (ID) values (1001);

insert into cliente (ID, CPF, NOME, IDADE, ENDERECO, CARRINHO_ID, TIPO_CLIENTE)
values(1001, 10020030006, 'Fulano', 23, 'Rua pb tal', 1001, 'NORMAL');

insert into item_produto (ID, PRODUTO_ID, QTD_ITENS)
values (1, 10005, 5);

insert into item_produto (ID, PRODUTO_ID, QTD_ITENS)
values (2, 10001, 4);

insert into carrinho_itens (CARRINHO_ID, ITENS_ID)
values (1001, 1);

insert into carrinho_itens (CARRINHO_ID, ITENS_ID)
values (1001, 2);
