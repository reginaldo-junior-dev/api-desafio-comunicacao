# API Desafio de Comunicação

API REST para gerenciamento de agendamentos de comunicação (envio de mensagens em data e hora futuras).

## Sumário

- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Configuração (variáveis de ambiente)](#configuração-variáveis-de-ambiente)
- [Opção 1: Rodar tudo com Docker Compose (recomendado)](#opção-1-rodar-tudo-com-docker-compose-recomendado)
- [Opção 2: Rodar localmente com Maven](#opção-2-rodar-localmente-com-maven)
- [Acessando o Swagger UI](#acessando-o-swagger-ui)
- [Usando a API pelo Swagger (passo a passo)](#usando-a-api-pelo-swagger-passo-a-passo)
- [Endpoints](#endpoints)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Solução de problemas](#solução-de-problemas)

---

## Tecnologias

| Tecnologia       | Versão | Finalidade                              |
|------------------|--------|-----------------------------------------|
| Java             | 21     | Linguagem de programação                |
| Spring Boot      | 4.1.1  | Framework principal                     |
| Maven            | Wrapper incluso (`mvnw`) | Gerenciador de dependências e build |
| PostgreSQL       | 18     | Banco de dados (via Docker)             |
| Spring Data JPA  | -      | Persistência                            |
| Springdoc OpenAPI| 3.1.0  | Documentação Swagger/OpenAPI            |
| Lombok / MapStruct | -    | Redução de boilerplate e mapeamento     |

## Pré-requisitos

Para rodar o projeto você precisa de:

- **JDK 21** (verifique com `java -version`)
- **Docker** e **Docker Compose** (para a Opção 1 e para subir o banco na Opção 2)
- **Maven** (opcional — o projeto já inclui o wrapper `mvnw` / `mvnw.cmd`)

> 💡 No Windows, use o `mvnw.cmd`. No Linux/Mac, use `./mvnw`.

## Configuração (variáveis de ambiente)

A aplicação lê as configurações de banco do arquivo `.env` na raiz do projeto. Crie o arquivo (ele **não** é versionado — já está no `.gitignore`) com o seguinte conteúdo, trocando a senha pela que você preferir:

```env
DB_URL=jdbc:postgresql://localhost:5432/api_desafio_comunicacao
DB_USERNAME=postgres
DB_PASSWORD=SUA_SENHA_AQUI
```

| Variável      | Descrição                                              |
|---------------|--------------------------------------------------------|
| `DB_URL`      | URL de conexão JDBC com o PostgreSQL                   |
| `DB_USERNAME` | Usuário do banco                                       |
| `DB_PASSWORD` | Senha do banco (defina a sua própria)                  |

- Ao rodar **sem Docker**, o Maven carrega este arquivo `.env` automaticamente.
- Ao rodar **com Docker Compose**, o arquivo `.env` é usado pelo compose para definir a senha do banco e as variáveis do container da API (o compose usa `${DB_PASSWORD}` de forma automática, então basta manter o `.env` consistente).
- ⚠️ **Atenção**: não publique nem versione a senha real. Use uma senha forte e mantenha o `.env` apenas localmente.

---

## Opção 1: Rodar tudo com Docker Compose (recomendado)

Este modo sobe **dois containers**: o PostgreSQL e a aplicação, e já faz o build da imagem da API.

### Passo 1 — Verifique se o Docker está rodando

```powershell
docker --version
docker compose version
```

### Passo 2 — (Primeira vez) Verifique o `.env`

Confira se o arquivo `.env` existe na raiz e contém a senha que você deseja usar para o PostgreSQL. Se você editar a senha, reinicie os containers.

### Passo 3 — Suba os containers

No diretório raiz do projeto:

```powershell
docker compose up --build
```

- `--build` força a reconstrução da imagem da API (necessário na primeira vez e sempre que o código mudar).
- Na primeira execução, o Maven vai baixar as dependências — pode demorar alguns minutos.

### Passo 4 — Verifique se subiu

```powershell
docker compose ps
```

Você deve ver dois containers com status `running`/`Up`:

- `api-desafio-comunicacao` (API na porta `8080`)
- `comunicacao-postgres` (PostgreSQL na porta `5432`)

### Passo 5 — Teste

Acesse: http://localhost:8080/swagger-ui/index.html

### Rodando em segundo plano

```powershell
docker compose up -d --build
```

### Ver logs

```powershell
docker compose logs -f api
```

### Parar os containers

```powershell
docker compose stop
```

### Parar e remover os containers (mantém os dados do banco)

```powershell
docker compose down
```

### Parar, remover containers **e apagar os dados do banco**

```powershell
docker compose down -v
```

> 💡 O volume `postgres_data` preserva os dados do banco entre execuções. Só use `-v` se quiser resetar tudo.

---

## Opção 2: Rodar localmente com Maven

Neste modo, a aplicação roda na sua máquina e só o banco de dados fica no Docker.

### Passo 1 — Suba apenas o PostgreSQL

Você pode subir o PostgreSQL sozinho com Docker:

```powershell
docker run --name comunicacao-postgres `
  -e POSTGRES_DB=api_desafio_comunicacao `
  -e POSTGRES_USER=postgres `
  -e POSTGRES_PASSWORD=SUA_SENHA_AQUI `
  -p 5432:5432 `
  -d postgres:18
```

> ⚠️ Use aqui a **mesma senha** definida no seu arquivo `.env`.

Ou, se preferir, edite o `docker-compose.yml` para subir só o serviço `postgres`:

```powershell
docker compose up -d postgres
```

### Passo 2 — Confira o `.env`

Como o banco está rodando na sua máquina, o `.env` deve estar configurado assim (use a senha que você definiu):

```env
DB_URL=jdbc:postgresql://localhost:5432/api_desafio_comunicacao
DB_USERNAME=postgres
DB_PASSWORD=SUA_SENHA_AQUI
```

> ⚠️ Se a porta `5432` já estiver em uso na sua máquina (por exemplo, um PostgreSQL instalado localmente), pare esse serviço antes ou troque a porta.

### Passo 3 — Rode a aplicação

**No Windows (PowerShell):**

```powershell
.\mvnw.cmd spring-boot:run
```

**No Linux/Mac:**

```bash
./mvnw spring-boot:run
```

### Passo 4 — Aguarde a subida

Quando aparecer algo como abaixo, a API está pronta:

```
Tomcat started on port 8080 (http) with context path '/'
Started ApiDesafioComunicacaoApplication
```

### Passo 5 — Teste

Acesse: http://localhost:8080/swagger-ui/index.html

### Build e execução do JAR (alternativa)

```powershell
.\mvnw.cmd clean package -DskipTests
java -jar target\api-desafio-comunicacao-0.0.1-SNAPSHOT.jar
```

### Rodar os testes

```powershell
.\mvnw.cmd test
```

---

## Acessando o Swagger UI

Com a aplicação rodando (por qualquer uma das opções acima):

1. Abra o navegador.
2. Acesse: **http://localhost:8080/swagger-ui/index.html**
3. Você verá a página do Swagger com o título **"API de comunicação"**.

URLs úteis:

| URL                                        | Descrição                    |
|--------------------------------------------|------------------------------|
| http://localhost:8080/swagger-ui/index.html| Interface gráfica (Swagger UI) |
| http://localhost:8080/v3/api-docs          | Documentação em JSON (OpenAPI) |

---

## Usando a API pelo Swagger (passo a passo)

### 1. Criar um agendamento (POST)

1. Na página do Swagger, localize o bloco **agendamento** (API para gerenciamento de agendamentos de comunicação).
2. Clique em `POST /agendamento` → **Cria um novo agendamento**.
3. Clique em **Try it out**.
4. No campo **Request body**, cole o JSON de exemplo (a data deve ser **futura**, formato `dd/MM/yyyy HH:mm:ss`):

```json
{
  "dataHora": "30/08/2026 18:00:00",
  "destinatario": "cliente@email.com",
  "mensagem": "Sua comunicação foi agendada com sucesso."
}
```

5. Clique em **Execute**.
6. Verifique a resposta **201 (Created)**:

```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "dataHora": "30/08/2026 18:00:00",
  "destinatario": "cliente@email.com",
  "mensagem": "Sua comunicação foi agendada com sucesso.",
  "statusAgendamento": "AGENDADO"
}
```

7. **Copie o `id` retornado** — ele será usado nos próximos passos.

### 2. Consultar um agendamento (GET)

1. No Swagger, clique em `GET /agendamento/{id}` → **Consulta um agendamento**.
2. Clique em **Try it out**.
3. No campo `id`, cole o UUID retornado no passo anterior. Exemplo:

```
3fa85f64-5717-4562-b3fc-2c963f66afa6
```

4. Clique em **Execute**.
5. Verifique a resposta **200 (OK)** com os dados do agendamento.

### 3. Cancelar um agendamento (PATCH)

1. No Swagger, clique em `PATCH /agendamento/{id}/cancelar` → **Cancela um agendamento**.
2. Clique em **Try it out**.
3. Cole o mesmo `id` no campo `id`.
4. Clique em **Execute**.
5. Verifique a resposta **200 (OK)**:

```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "dataHora": "30/08/2026 18:00:00",
  "destinatario": "cliente@email.com",
  "mensagem": "Sua comunicação foi agendada com sucesso.",
  "statusAgendamento": "CANCELADO"
}
```

> Se você tentar cancelar duas vezes o mesmo agendamento, receberá **422** com a mensagem "O agendamento já está cancelado".

---

## Endpoints

| Método | Rota                          | Descrição                     | Respostas                        |
|--------|-------------------------------|-------------------------------|----------------------------------|
| POST   | `/agendamento`                | Cria um novo agendamento      | 201 (sucesso), 422 (dados inválidos) |
| GET    | `/agendamento/{id}`           | Consulta um agendamento por ID | 200 (encontrado), 404 (não encontrado) |
| PATCH  | `/agendamento/{id}/cancelar`  | Cancela um agendamento        | 200 (cancelado), 404 (não encontrado), 422 (já cancelado) |

### Corpo da requisição (POST)

| Campo          | Tipo       | Obrigatório | Validação                                        | Exemplo                    |
|----------------|------------|-------------|--------------------------------------------------|----------------------------|
| `dataHora`     | `string`   | Sim         | Data futura, formato `dd/MM/yyyy HH:mm:ss`       | `"30/08/2026 18:00:00"`    |
| `destinatario` | `string`   | Sim         | Não pode ser vazio                               | `"cliente@email.com"`      |
| `mensagem`     | `string`   | Sim         | Não pode ser vazio                               | `"Olá, tudo bem?"`         |

### Corpo da resposta

| Campo               | Tipo     | Descrição                                        |
|---------------------|----------|--------------------------------------------------|
| `id`                | `uuid`   | Identificador único gerado automaticamente       |
| `dataHora`          | `string` | Data/hora do envio (`dd/MM/yyyy HH:mm:ss`)       |
| `destinatario`      | `string` | Destinatário da comunicação                      |
| `mensagem`          | `string` | Mensagem a ser enviada                           |
| `statusAgendamento` | `string` | `AGENDADO` ou `CANCELADO`                        |

### Exemplo completo via `curl`

```bash
curl -X POST http://localhost:8080/agendamento \
  -H "Content-Type: application/json" \
  -d '{"dataHora":"30/08/2026 18:00:00","destinatario":"cliente@email.com","mensagem":"Olá, tudo bem?"}'
```

---

## Estrutura do projeto

```
src/main/java/reginaldo/api_desafio_comunicacao/
├── ApiDesafioComunicacaoApplication.java   # Classe principal (main)
├── config/
│   └── OpenAPIconfig.java                  # Configuração do Swagger/OpenAPI
├── controller/
│   └── AgendamentoController.java          # Endpoints REST (/agendamento)
├── DTO/
│   ├── AgendamentoRequest.java             # DTO de entrada (com validações)
│   └── AgendamentoResponse.java            # DTO de saída
├── entity/
│   └── Agendamento.java                    # Entidade JPA
├── ENUM/
│   └── StatusAgendamento.java              # AGENDADO / CANCELADO
├── exception/
│   ├── AgendamentoCancelado.java
│   ├── AgendamentoNaoEncontrado.java
│   ├── ErrorResponse.java
│   └── GlobalExceptionHandler.java         # Tratamento global de erros
├── mapper/
│   └── AgendamentoMapper.java              # MapStruct (DTO ↔ Entity)
├── repository/
│   └── AgendamentoRepository.java          # Spring Data JPA
└── service/
    └── AgendamentoService.java             # Regras de negócio
```

---

## Solução de problemas

| Problema                                         | Solução                                                                                          |
|--------------------------------------------------|--------------------------------------------------------------------------------------------------|
| Erro `Connection refused` ao iniciar a API local | O PostgreSQL não está rodando. Suba o banco com `docker compose up -d postgres` ou `docker run` (Opção 2, Passo 1). |
| Porta `5432` já em uso                           | Existe outro PostgreSQL na máquina. Pare o serviço ou mude a porta no `.env` e no comando/port do banco. |
| Porta `8080` já em uso                           | Encontre o processo com `netstat -ano \| findstr :8080` e encerre, ou mude a porta em `application.properties` (`server.port=8081`) e no `docker-compose.yml`. |
| Swagger não abre                                  | Confirme que a API subiu (log `Started ApiDesafioComunicacaoApplication`) e acesse exatamente `http://localhost:8080/swagger-ui/index.html`. |
| `docker compose up` falha no build               | Verifique se o Docker está rodando e rode novamente com `--build`. No Windows, certifique-se de que o `.gitattributes` configurou o `mvnw` com `eol=lf` (já configurado no projeto). |
| Erro de validação 422 ao criar agendamento       | A `dataHora` deve ser futura e no formato `dd/MM/yyyy HH:mm:ss`; `destinatario` e `mensagem` não podem ser vazios. |
| Quer zerar o banco de dados                      | Rode `docker compose down -v` e suba novamente (apaga todos os dados). |
