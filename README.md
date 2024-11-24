# apiContatos

> **Use os endpoints da API remotamente pelo:**
> [<img style="width: 14px; vertical-align: middle;" src="https://github.com/user-attachments/assets/75a41f77-2da3-4687-b753-c682ea670cc5"> Swagger UI](https://apicontatos.onrender.com/swagger-ui/index.html)
<br>_(O link pode demorar de um a dois minutos para abrir)_

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
src/
└───main/
    └───java/
        └───br/
            └───com/
                └───cotiinformatica/
                    └───factories/
                        ConnectionFactory.java
````
4. Abra o arquivo `script.sql`. Execute esse script no seu banco de dados recém-criado para criar a tabela necessária.
````
src/
└───main/
    └───java/
        └───br/
            └───com/
                └───cotiinformatica/
                    └───sql/
                        script.sql
````
## Dependências de bibliotecas do ``pom.xml``
- [Spring Boot DevTools](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools)
- [Spring Web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
- [Jakarta Bean Validation](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation)
- [PostgreSQL Driver](https://mvnrepository.com/artifact/org.postgresql/postgresql)
- [Swagger UI](https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui)
- [Lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok)
- [ModelMapper Spring](https://mvnrepository.com/artifact/org.modelmapper.extensions/modelmapper-spring)
- [Java Faker](https://mvnrepository.com/artifact/com.github.javafaker/javafaker)
## Diagrama de Classes do relacionamento das entidades
![](https://github.com/user-attachments/assets/285d060f-30c4-4305-80d9-fa45277f8451)
## Endpoints da API
![](https://github.com/user-attachments/assets/4e5cf812-0a72-4429-88ff-0c5770cc57a5)