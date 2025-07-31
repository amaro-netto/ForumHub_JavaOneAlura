# Fórum Hub API

![Java](https://img.shields.io/badge/Java-21-red.svg)
![Spring](https://img.shields.io/badge/Spring-Boot_3.3.1-green.svg)
![JPA](https://img.shields.io/badge/JPA-Hibernate-blue.svg)
![Security](https://img.shields.io/badge/Spring_Security-6.3-lightgreen.svg)
![JWT](https://img.shields.io/badge/JWT-Auth0-black.svg)
![API](https://img.shields.io/badge/API-REST-yellow.svg)
![H2](https://img.shields.io/badge/Database-H2-darkgreen.svg)
![Validation](https://img.shields.io/badge/Validation-Jakarta-orange.svg)
![Lombok](https://img.shields.io/badge/Lombok-v1.18.32-purple.svg)
![Swagger](https://img.shields.io/badge/Docs-Swagger_UI-blue.svg)

Bem-vindo ao repositório do projeto Fórum Hub, uma API RESTful construída com Java e o framework Spring Boot. Este projeto faz parte do **Challenge Back-end** do programa Oracle Next Education (ONE).

O objetivo deste desafio é simular o dia a dia de um desenvolvedor back-end, aplicando conceitos de Java e Spring Boot para criar uma API completa. A API gerencia um fórum, permitindo a criação, listagem, atualização e exclusão de tópicos por usuários autenticados.

## Funcionalidades da API

A API Fórum Hub oferece os seguintes endpoints para gerenciamento de tópicos e autenticação de usuários:

* **Autenticação de Usuários**:
    * `POST /usuarios`: Cadastra um novo usuário.
    * `POST /login`: Autentica um usuário e retorna um token JWT.

* **Gerenciamento de Tópicos**:
    * `POST /topicos`: Cria um novo tópico (requer autenticação).
    * `GET /topicos`: Lista todos os tópicos, com suporte a paginação.
    * `GET /topicos/{id}`: Retorna os detalhes de um tópico específico.
    * `PUT /topicos`: Atualiza um tópico existente (requer autenticação).
    * `DELETE /topicos/{id}`: Deleta um tópico (requer autenticação).

## Tecnologias Utilizadas

* **Linguagem**: Java 21
* **Framework**: Spring Boot 3.3.1
* **Banco de Dados**: H2 Database (em memória para desenvolvimento)
* **ORM**: Spring Data JPA com Hibernate
* **Segurança**: Spring Security com JSON Web Tokens (JWT)
* **Validação**: Jakarta Validation
* **Documentação**: SpringDoc OpenAPI com Swagger UI
* **Ferramentas**: Maven, Lombok

## Como Rodar o Projeto

1.  **Pré-requisitos**:
    * Java JDK 21 instalado.
    * Maven instalado.
    * Uma IDE como IntelliJ IDEA ou VS Code.

2.  **Clone o Repositório**:
    ```bash
    git clone [URL-DO-SEU-REPOSITORIO-AQUI]
    cd nome-do-seu-projeto
    ```

3.  **Configurar o `application.properties`**:
    * Abra o arquivo `src/main/resources/application.properties`.
    * Defina uma chave secreta para a autenticação JWT:
        ```properties
        api.security.token.secret=SUA_CHAVE_SECRETA_UNICA_E_SEGURA_AQUI
        ```

4.  **Executar a Aplicação**:
    * Na sua IDE, execute a classe `ForumhubApplication.java`.
    * Ou, via terminal, na raiz do projeto:
        ```bash
        ./mvnw spring-boot:run
        ```
    A API estará rodando em `http://localhost:8080`.

## Documentação e Testes da API

Para explorar e testar todos os endpoints, acesse a documentação interativa gerada pelo Swagger UI:

* **Swagger UI**: `http://localhost:8080/swagger-ui.html`

## Desafios e Aprendizados

Durante o desenvolvimento deste projeto, tive a oportunidade de aprofundar meu conhecimento em:

* **Arquitetura em Camadas**: Estrutura a aplicação em `controller`, `service`, `repository` e `model`, seguindo as melhores práticas de desenvolvimento.
* **Segurança com JWT**: Implementação completa de autenticação e autorização com Spring Security e JWT, garantindo que apenas usuários logados possam acessar recursos restritos.
* **Validação de Dados**: Uso de anotações da Jakarta Validation para garantir a integridade dos dados recebidos nos DTOs.
* **Mapeamento JPA**: Configuração de relacionamentos entre entidades (`@ManyToOne`) e o uso de métodos de busca customizados no Spring Data JPA.

Este projeto foi uma experiência prática valiosa para aplicar as aprendizagens obtidas nos cursos de Spring Boot 3 da Alura.

---
Agradeço por ter chegado até aqui! Caso tenha alguma dúvida ou sugestão, sinta-se à vontade para entrar em contato.
