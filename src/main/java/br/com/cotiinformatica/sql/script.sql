--Criar tabela de categoria
create table categoria(
			id			UUID			primary key,
			nome		varchar(29)		not null  unique
);

--Criar tabela de contatos
create table contato(

			id				UUID			primary key,
			nome			varchar(25)		not null,
			telefone			varchar(15)		not null,
			categoria_id		uuid			not null,
			foreign key(categoria_id)
				references categoria(id)
);

--Cadastrar categorias
insert into categoria (id, nome) values (gen_random_uuid(), 'Categoria pessoa física');
insert into categoria (id, nome) values (gen_random_uuid(), 'Categoria pessoa jurídica');
insert into categoria (id, nome) values (gen_random_uuid(), 'Categoria pessoa preferencial');
insert into categoria (id, nome) values (gen_random_uuid(), 'Categoria pessoa vip');