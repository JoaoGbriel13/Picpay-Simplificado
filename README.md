
---

# PicPay Simplificado

## Visão Geral

O PicPay Simplificado é uma plataforma de pagamentos que permite realizar depósitos e transferências de dinheiro entre usuários. O sistema suporta dois tipos de usuários: comuns e lojistas. Os usuários têm uma carteira com saldo e podem transferir dinheiro entre si, enquanto os lojistas só recebem transferências.

## Requisitos

### Requisitos Funcionais

1. **Cadastro de Usuários**
   - Nome Completo, CPF, e-mail e Senha são obrigatórios.
   - CPF/CNPJ e e-mails devem ser únicos no sistema.

2. **Transferências**
   - Usuários podem transferir dinheiro para outros usuários e lojistas.
   - Lojistas só podem receber transferências.
   - O saldo deve ser validado antes da transferência.

3. **Autorização Externa**
   - Antes de finalizar a transferência, o sistema consulta um serviço autorizador externo.
   - [Mock de Autorização](https://util.devi.tools/api/v2/authorize) para simular a autorização.

4. **Notificações**
   - Usuários e lojistas recebem notificações após o recebimento de um pagamento.
   - [Mock de Notificação](https://util.devi.tools/api/v1/notify) para simular o envio de notificações.

5. **Transações**
   - A operação de transferência deve ser tratada como uma transação.
   - Em caso de falha, o dinheiro deve ser revertido para o usuário que enviou.

## Tecnologias Utilizadas

- **Spring Boot**: Framework principal para desenvolvimento da aplicação.
- **Docker Compose**: Para orquestração de contêineres e configuração do ambiente.
- **OpenFeign**: Para comunicação com serviços externos, como o serviço de autorização e notificação.
- **ModelMapper**: Para mapeamento entre DTOs e entidades.
- **Spring Data JPA**: Para acesso a dados e gerenciamento de entidades no banco de dados.
- **ProblemDetail**: Para padronização de erros e mensagens de problema.

## Configuração

### Docker Compose

O ambiente é configurado usando Docker Compose, o que permite orquestrar contêineres para o banco de dados PostgreSQL e a aplicação Spring Boot. O mapeamento da porta `63800` para a porta `5432` do contêiner PostgreSQL está configurado para facilitar a comunicação entre a aplicação e o banco de dados.

### Feign Clients

- **AuthorizationController**: Cliente Feign para consultar o serviço de autorização externo.
- **NotifyController**: Cliente Feign para enviar notificações após a transferência.

### Mapeamento e Erro

- **ModelMapper**: Configurado para mapear entre DTOs e entidades.
- **Handlers Personalizados**:
   - **PicPayHandler**: Manipula erros de autorização com status HTTP 403 (Forbidden).
   - **InsuficentBalanceHandler**: Manipula erros de saldo insuficiente com status HTTP 409 (Conflict).
   - **UserNotAllowedException**: Lançada quando um lojista tenta realizar uma transferência. Retorna um `ProblemDetail` com status `METHOD_NOT_ALLOWED`.
   - **UserNotFoundException**: Lançada quando um usuário não é encontrado. Retorna um `ProblemDetail` com status `UNPROCESSABLE_ENTITY`.

### Endpoints

- **POST /transaction**: Realiza uma transferência de dinheiro entre usuários ou para lojistas.
- **POST /register**: Cadastra um novo usuário no sistema.

## Estrutura do Projeto

- **`client`**: Contém os clientes Feign para comunicação com serviços externos.
- **`config`**: Configurações da aplicação, como o ModelMapper.
- **`controller`**: Controladores REST que expõem endpoints para interagir com a aplicação.
- **`dto`**: Objetos de Transferência de Dados utilizados para comunicação entre diferentes camadas.
   - **`TransferDTO`**: Define os dados necessários para uma transferência, incluindo o valor, o pagador e o recebedor. Agora usa `BigDecimal` para valores e `long` para IDs.
- **`handler`**: Manipuladores de exceções e erros personalizados.
   - **`UserNotAllowedException`**: Manipula erros quando um lojista tenta transferir dinheiro.
   - **`UserNotFoundException`**: Manipula erros quando um usuário não é encontrado.
- **`mapper`**: Mapeadores que convertem entre DTOs e entidades.
   - **`TransferMapper`**: Utiliza `ModelMapper` para converter entre `TransferDTO` e `Transfer`.
- **`model`**: Entidades e enums utilizadas pela aplicação.
   - **`Transfer`**: Representa uma transferência entre usuários com informações sobre o valor e os usuários envolvidos. Inclui `payer` e `payee` como relacionamentos com a entidade `User`.
- **`repository`**: Repositórios para acesso a dados.
   - **`TransferRepository`**: Interface para operações CRUD na entidade `Transfer`.
- **`service`**: Serviços que contêm a lógica de negócio.
   - **`AuthorizationService`**: Interface para verificar se uma transferência é autorizada.
   - **`NotificationService`**: Define o serviço para enviar notificações.
   - **`UserService`**: Interface para operações relacionadas a usuários e transferências.
   - **`NotificationServiceImpl`**: Implementa o serviço de notificação usando o `NotifyController`.
   - **`UserServiceimpl`**: Implementa o serviço de usuário, incluindo a lógica para realizar transferências e registrar usuários.

## Instruções de Execução

1. **Configuração do Docker Compose**: Execute `docker-compose up` para iniciar a aplicação e o banco de dados.
2. **Execução da Aplicação**: Inicie a aplicação Spring Boot com `mvn spring-boot:run` ou através do IDE de sua escolha.

## Testes

Os testes são realizados com o framework Mockito para criar mocks dos componentes necessários.

- **`PicPayTest`**:
   - **`authorizedTransaction`**: Verifica se uma transferência autorizada é processada corretamente e os saldos são ajustados.
   - **`unauthorizedTransaction`**: Verifica se uma tentativa de transferência não autorizada é tratada corretamente.

Execute os testes com o Maven:

```bash
mvn test
```

Certifique-se de que todos os mocks estão corretamente configurados e inicializados antes de executar os testes para garantir a precisão dos resultados.

## Contribuições

Para contribuir com o projeto, faça um fork do repositório, crie uma branch com suas alterações e envie um pull request. Certifique-se de seguir as melhores práticas de codificação e de realizar testes antes de enviar suas contribuições.

---

