--Criar tabela de categoria
CREATE TABLE categoria(
			id			UUID				PRIMARY KEY,
			nome		VARCHAR(29)		NOT NULL UNIQUE
);

--Criar tabela de contatos
CREATE TABLE contato(

			id				UUID				PRIMARY KEY,
			nome			VARCHAR(25)		NOT NULL,
			telefone			VARCHAR(15)		NOT NULL,
			categoria_id		uuid				NOT NULL,
			FOREIGN KEY(categoria_id)
				REFERENCES categoria(id)
);

--Cadastrar categorias
INSERT INTO categoria (id, nome) VALUES (gen_random_uuid(), 'Categoria pessoa física');
INSERT INTO categoria (id, nome) VALUES (gen_random_uuid(), 'Categoria pessoa jurídica');
INSERT INTO categoria (id, nome) VALUES (gen_random_uuid(), 'Categoria pessoa preferencial');
INSERT INTO categoria (id, nome) VALUES (gen_random_uuid(), 'Categoria pessoa vip');