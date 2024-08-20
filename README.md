# apiContatos

Este é um projeto Java Spring Boot que maneja as entidades "Contato" e "Categoria" usando uma API RESTFUL.

## Pré-requisitos

Antes de começar, certifique-se de ter os seguintes pré-requisitos instalados (entre parênteses com asterisco estão as versões que utilizei, outras versões também podem funcionar.):

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) (*17)
- [PostgreSQL](https://www.postgresql.org/download/) (*16)

## Configuração do Banco de Dados

1. Instale o PostgreSQL seguindo as instruções no [site oficial](https://www.postgresql.org/download/).
2. Crie um banco de dados no PostgreSQL para ser utilizado pelo projeto.
3. Vá na classe ConnectionFactory e altere os atributos `url`, `user` e `password` para as suas configurações no pgAdmin.
````
└───src
    └───main
        └───java
            └───br
                └───com
                    └───cotiinformatica
                        └───factories
                                ConnectionFactory.java
````
4. Abra o arquivo `script.sql`. Execute esse script no seu banco de dados recém-criado para criar a tabela necessária.
````
└───src
    └───main
        └───java
            └───br
                └───com
                    └───cotiinformatica
                        └───sql
                                script.sql
````

## Dependências de bibliotecas do ``pom.xml``
- Spring Boot
- Spring Web
- PostgreSQL
- Swagger
- Lombok
- Jakarta Bean Validation
- ModelMapper
- Java Faker

## Diagrama de Classes do relacionamento das entidades
![](https://github.com/user-attachments/assets/285d060f-30c4-4305-80d9-fa45277f8451)

## Endpoints da API
![](https://github.com/user-attachments/assets/4e5cf812-0a72-4429-88ff-0c5770cc57a5)