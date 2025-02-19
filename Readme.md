# Projeto Barbearia

## Descrição
Este projeto é um sistema de agendamento de cortes de cabelo em uma barbearia. Ele foi desenvolvido com Spring Boot e utiliza o PostgreSQL como banco de dados. A aplicação permite que os usuários agendem cortes, visualizem os horários disponíveis, e interajam com barbeiros.

## Pré-requisitos

- Java 11 ou superior
- Maven
- PostgreSQL

## Configuração do Banco de Dados

1. Navegue até o diretório do projeto:
    ```bash
    cd barbearia
    ```

2. Abra o arquivo `application.properties` e configure as credenciais do banco de dados:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_banco
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    spring.jpa.hibernate.ddl-auto=update
    ```

## Executando a Aplicação

1. **Compile e execute a aplicação com Maven:**
    ```bash
    mvn spring-boot:run
    ```

2. **A aplicação estará disponível na URL:**
    ```
    http://localhost:8080
    ```

## Endpoints

### 1. Agendamento de Corte

- **Método:** POST
- **Endpoint:** `/api/agendamento`
- **Descrição:** Agende um corte com um barbeiro.

**Corpo da requisição (JSON):**
```json
{
    "cliente": "João da Silva",
    "dataHora": "2025-02-20T14:00:00",
    "barbeiroId": 1
}


