# Labfoods API

## Descrição

Labfoods é uma API REST desenvolvida para fornecer receitas de culinária do mundo todo. A API permite que os usuários explorem, descubram novas receitas, encontrem dicas de comida e compartilhem suas experiências. Além disso, os usuários podem se cadastrar, gerenciar suas receitas e avaliar receitas de outros usuários.

## Tecnologias Utilizadas

- **Linguagem**: Java
- **Framework**: Spring Boot
- **Banco de Dados**: PostgreSQL
- **Segurança**: Spring Security (Basic Auth)
- **Testes**: Postman

## Estrutura do Projeto

```plaintext
src
└── main
    ├── java
    │   └── br
    │       └── com
    │           └── senai
    │               └── labfoods
    │                   ├── controllers
    │                   ├── dtos
    │                   ├── exceptions
    │                   ├── models
    │                   ├── repositories
    │                   ├── services
    │                   └── LabfoodsApplication.java
    └── resources
        ├── application.properties
        └── data.sql


```

## Endpoints

### Autenticação

- `POST /api/login`
  - Realiza a autenticação dos usuários no sistema.

### Usuários

- `POST /api/usuarios`
  - Cria um novo usuário.
- `GET /api/usuarios`
  - Lista todos os usuários.
- `GET /api/usuarios/{id}`
  - Busca um usuário pelo ID.
- `PUT /api/usuarios/{id}`
  - Atualiza um usuário.
- `DELETE /api/usuarios/{id}`
  - Deleta um usuário.

### Receitas

- `POST /api/receitas`
  - Cria uma nova receita.
- `GET /api/receitas`
  - Lista todas as receitas.
- `GET /api/receitas/{id}`
  - Busca uma receita pelo ID.
- `PUT /api/receitas/{id}`
  - Atualiza uma receita.
- `DELETE /api/receitas/{id}`
  - Deleta uma receita.
- `GET /api/receitas/top-rated`
  - Lista as top 3 receitas mais bem avaliadas.

### Avaliações

- `POST /api/avaliacoes`
  - Cria uma nova avaliação.
- `GET /api/avaliacoes`
  - Lista todas as avaliações.
- `GET /api/avaliacoes/{id}`
  - Busca uma avaliação pelo ID.
- `PUT /api/avaliacoes/{id}`
  - Atualiza uma avaliação.
- `DELETE /api/avaliacoes/{id}`
  - Deleta uma avaliação.

### Dashboard

- `GET /api/dashboard`
  - Fornece estatísticas gerais sobre usuários e receitas cadastradas, além das top 3 receitas mais bem avaliadas.

## Regras de Negócio

- Não é permitido cadastrar mais de um usuário com o mesmo CPF.
- Não é permitido deletar usuários que possuam receitas associadas.
- Usuários não podem votar em suas próprias receitas.
- Dados sensíveis como CPF, endereço, data de nascimento e senha de login não podem ser listados.
- CPF não pode ser editado após o cadastro.

## Configuração

### Requisitos

- Java 11 ou superior
- PostgreSQL

### Configuração do Banco de Dados

No arquivo `application.properties`, configure as propriedades do banco de dados:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/labfoods
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update

```

## Executando a Aplicação

Para executar a aplicação, utilize o seguinte comando:

```bash
./mvnw spring-boot:run

```

## Carregamento Inicial de Dados

O arquivo `data.sql` contém os dados iniciais para o sistema, incluindo 5 usuários fictícios.

## Testes

Utilize o Postman para testar os endpoints da API. Exemplos de requisições podem ser encontrados na seção de Endpoints deste documento.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.

## Contato

Para dúvidas ou mais informações, entre em contato com o desenvolvedor:

- **Nome**: [Manoel Cavenati]
- **E-mail**: [manoelcavenati@gmail.com]
- **GitHub**: [[Manoel Cavenati](https://github.com/ManoelCavenati)]
