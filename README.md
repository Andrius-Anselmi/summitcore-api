<div align="center">

# SummitCore
### Uma REST API production-grade para gerenciamento de eventos.
### Construída com Clean Architecture — use cases isolados, domínio protegido, zero vazamento de infraestrutura.
&nbsp;

[![Java](https://img.shields.io/badge/java-17_LTS-ED8B00?style=flat-square&labelColor=0a0e14&logo=openjdk&logoColor=ED8B00)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/spring_boot-3.x-6DB33F?style=flat-square&labelColor=0a0e14&logo=spring&logoColor=6DB33F)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/postgresql-15+-4169E1?style=flat-square&labelColor=0a0e14&logo=postgresql&logoColor=4169E1)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/docker-compose-2496ED?style=flat-square&labelColor=0a0e14&logo=docker&logoColor=2496ED)](https://www.docker.com/)
[![Flyway](https://img.shields.io/badge/flyway-migrations-CC0200?style=flat-square&labelColor=0a0e14&logo=flyway&logoColor=white)](https://flywaydb.org/)
[![Maven](https://img.shields.io/badge/maven-3.8+-C71A36?style=flat-square&labelColor=0a0e14&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![CI/CD](https://img.shields.io/badge/github_actions-CI%2FCD-2088FF?style=flat-square&labelColor=0a0e14&logo=githubactions&logoColor=2088FF)](https://github.com/features/actions)
[![Status](https://img.shields.io/badge/status-em_desenvolvimento-F0A500?style=flat-square&labelColor=0a0e14)](.)
[![License](https://img.shields.io/badge/licença-MIT-b0e8ff?style=flat-square&labelColor=0a0e14)](./LICENSE)

&nbsp;

🌐 **Language / Idioma:** [English](./README.md) · Português

&nbsp;

[Início Rápido](#início-rápido) · [Arquitetura](#arquitetura) · [API Reference](#api-reference) · [Decisões de Design](#decisões-de-design) · [Roadmap](#roadmap)

&nbsp;

| | | |
|---|---|---|
| **Clean Architecture** aplicada com rigor | **Use Cases** como cidadãos de primeira classe | Config **12-Factor** compliant |
| **Zero** vazamento do domain model | **Custom exceptions** & handler global | Schema versionado com **Flyway** |
| Identificadores **gerados automaticamente** | CI/CD com **GitHub Actions** | Deploy-ready com **Docker** |

</div>

---

## O Problema

Durante o desenvolvimento de APIs, é comum começar com uma estrutura simples: controllers chamando services, services acessando repositories e entidades sendo retornadas diretamente nas respostas.

Embora essa abordagem funcione em projetos menores, ela pode gerar problemas conforme a aplicação cresce, como acoplamento entre camadas, dificuldade de manutenção e dependência direta da estrutura do banco de dados.

O SummitCore foi desenvolvido buscando uma arquitetura mais organizada e escalável, utilizando Clean Architecture para separar regras de negócio da infraestrutura.

O projeto aplica:
- Use Cases para centralizar a lógica de negócio;
- DTOs para separar os contratos da API das entidades de persistência;
- Tratamento global de exceções;
- Flyway para gerenciamento de migrations;
- Configuração através de variáveis de ambiente.

---

## Início Rápido

**Pré-requisitos:** JDK 17+, Maven 3.8+, Docker

```bash
git clone https://github.com/your-username/summitcore-api.git
cd summitcore-api
```

Inicie o PostgreSQL via Docker:

```bash
docker compose up -d
```

Configure as variáveis de ambiente (ou use seu `.env`):

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/summitcore
export SPRING_DATASOURCE_USERNAME=your_username
export SPRING_DATASOURCE_PASSWORD=your_password
```

Build e execução:

```bash
mvn clean install
mvn spring-boot:run
```

API disponível em `http://localhost:8080`. Nenhuma configuração manual de schema necessária — o Flyway executa as migrations automaticamente na inicialização.

---

## Arquitetura

Clean Architecture. O domínio não sabe nada sobre Spring, JPA ou PostgreSQL. A infraestrutura se adapta ao domínio — nunca o contrário.

```
  HTTP Request
       │
       ▼
┌─────────────────────────────────────────────┐
│  Presentation Layer                         │
│  EventController                            │
│  Gerencia HTTP, rotas e status codes        │
│  Retorna DTOs — nunca domain models         │
└────────────────────┬────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────┐
│  Core — Use Cases                           │
│  CreateEventCase / FindAllEventCase         │
│  FindByIdEventCase / FilterEventCase        │
│  DeleteEventByIdCase / UpdateEventCase      │
│  Todas as regras de negócio vivem aqui      │
│  Depende apenas de interfaces Gateway       │
└────────────────────┬────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────┐
│  Core — Gateway (interface)                 │
│  EventGateway                               │
│  Fronteira entre domínio e infraestrutura   │
└────────────────────┬────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────┐
│  Infrastructure — Gateway (impl)            │
│  EventRepositoryGateway                     │
│  Implementa EventGateway via JPA            │
│  Usa EventEntityMapper para conversão       │
└────────────────────┬────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────┐
│  PostgreSQL 15+ (Docker)                    │
│  Schema gerenciado por Flyway migrations    │
│  Nunca exposto ao domínio diretamente       │
└─────────────────────────────────────────────┘
```

### A divisão Core / Infrastructure

O package `core` é domínio puro — entidades, enums, interfaces e implementações de use cases, e a interface `EventGateway`. Ele não tem nenhuma dependência de Spring Data, JPA ou qualquer preocupação de infraestrutura.

O package `infrastructure` é onde o Spring vive — `EventRepositoryGateway` implementa `EventGateway`, `EventRepository` se comunica com o PostgreSQL, os mappers convertem entre entidades de domínio e entidades JPA, e o `BeanConfiguration` conecta tudo.

---

## API Reference

### Eventos

| Método | Rota                           | Descrição                         | Status        |
|--------|--------------------------------|-----------------------------------|---------------|
| `GET` | `api/events`                   | Lista todos os eventos            | `200`         |
| `GET` | `api/events/{id}`              | Busca evento por ID               | `200` / `404` |
| `GET` | `api/events/filter/identifier` | Filtra eventos pelo identificador | `200` / `404` |
| `POST` | `api/events`                   | Cria um novo evento               | `201` / `409` |
| `PUT` | `api/events/{id}`              | Atualiza um evento                | `200` / `404` |
| `DELETE` | `api/events/{id}`              | Remove um evento por ID           | `204` / `404` |

---

## Exemplos de Request

**Criando um evento:**

```bash
curl -X POST http://localhost:8080/events \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Spring Boot Workshop",
    "description": "Sessão prática sobre Spring Boot 3 e Clean Architecture.",
    "date": "2026-07-15",
    "type": "WORKSHOP"
  }'
```

```json
{
  "success": true,
  "data": {
    "identifier": "b3f1c2d4-e5a6-7890-abcd-ef1234567890",
    "name": "Spring Boot Workshop",
    "description": "Sessão prática sobre Spring Boot 3 e Clean Architecture.",
    "date": "2026-07-15",
    "type": "WORKSHOP"
  }
}
```

**Atualizando um evento:**

```bash
curl -X PUT http://localhost:8080/events/b3f1c2d4-e5a6-7890-abcd-ef1234567890 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Spring Boot Workshop — Avançado",
    "description": "Deep dive em Spring Boot 3, Clean Architecture e estratégias de teste.",
    "date": "2026-07-20",
    "type": "WORKSHOP"
  }'
```

```json
{
  "success": true,
  "data": {
    "identifier": "b3f1c2d4-e5a6-7890-abcd-ef1234567890",
    "name": "Spring Boot Workshop — Avançado",
    "description": "Deep dive em Spring Boot 3, Clean Architecture e estratégias de teste.",
    "date": "2026-07-20",
    "type": "WORKSHOP"
  }
}
```

> Todas as respostas são encapsuladas em `ApiResponse` — um envelope consistente com um campo `success` e um campo `data`. O `identifier` é um UUID gerado automaticamente pelo domínio na criação — o cliente nunca precisa fornecê-lo.

---

## Tratamento de Exceções

Os erros são tratados globalmente via `GlobalExceptionHandler`. Nenhum bloco try/catch espalhado pelos controllers.

| Exceção | HTTP Status | Quando |
|---------|-------------|--------|
| `DuplicateEventException` | `409 Conflict` | Evento com os mesmos dados já existe |
| `EventNotFoundException` | `404 Not Found` | Recurso não encontrado pelo identifier |

Todas as respostas de erro seguem o mesmo envelope `ApiResponse` das respostas de sucesso — formato previsível em toda a API.

---

## Decisões de Design

Cada padrão aqui é uma escolha deliberada, não boilerplate.

**Clean Architecture** — O domínio fica no centro e não depende de nada. A infraestrutura se adapta a ele. Isso significa que o banco de dados, o framework e a camada HTTP podem todos mudar sem tocar em nenhum use case.

**Use Cases como interfaces + implementações** — `CreateEventCase` é uma interface; `CreateEventCaseImpl` é sua implementação. Use cases são injetados pela interface, mantendo os chamadores desacoplados da lógica concreta. Trocar implementações ou fazer mock em testes não exige nenhuma refatoração.

**EventGateway como fronteira** — O domínio nunca chama um repository diretamente. Ele chama `EventGateway`, uma interface que ele mesmo possui. O `EventRepositoryGateway` na camada de infraestrutura implementa essa interface. A direção de dependência sempre aponta para dentro.

**Identifier gerado automaticamente** — O `identifier` (UUID) é gerado pela camada de domínio na criação, não delegado à sequence do banco de dados. Isso mantém o domínio no controle de sua própria identidade — nenhuma preocupação de infraestrutura vaza para o core.

**Dual mapper pattern** — `EventMapper` converte entre entidades de domínio e DTOs para a presentation layer. `EventEntityMapper` converte entre entidades de domínio e entidades JPA para a persistence layer. Cada mapper tem uma única responsabilidade bem definida.

**ApiResponse envelope** — Todas as respostas — sucesso e erro — compartilham o mesmo wrapper. Os clients sempre sabem o formato que vão receber, independentemente do endpoint.

**EventType enum** — Os tipos de evento não são strings livres no banco de dados. São um enum de primeira classe no domínio, aplicado no nível de tipo antes que qualquer coisa chegue à persistência.

**Tratamento global de exceções** — `@RestControllerAdvice` intercepta exceções tipadas e as mapeia para respostas HTTP. Os controllers ficam limpos. O formato de erro é consistente em toda a API.

**Flyway migrations** — Mudanças de schema são arquivos SQL versionados. Todo ambiente executa exatamente as mesmas migrations na mesma ordem. Nenhum schema drift entre máquinas.

**Config 12-Factor** — As credenciais do banco de dados vêm de variáveis de ambiente. O mesmo artefato roda em qualquer ambiente sem modificação.

**GitHub Actions CI/CD** — Build, test e deploy são automatizados via workflow files. Todo push para `main` aciona o pipeline — sem passos manuais, sem surpresas por ambiente.

---

## Database Schema

```
┌──────────────────────────────┐
│            event             │
│──────────────────────────────│
│ identifier  (UUID, PK)       │
│ name                         │
│ description                  │
│ date                         │
│ type        (EventType)      │
└──────────────────────────────┘
```

O schema é versionado via Flyway. `V1__create_table_event.sql` define a estrutura inicial. `V2__rename_column_identify_to_identifier.sql` corrige o nome da coluna. A fonte da verdade é sempre a cadeia de migrations — não o `ddl-auto` do Hibernate.

---

## Estrutura do Projeto

```
src/
└── main/
    └── java/
        └── com/summitcore/
            ├── core/
            │   ├── entities/       # Domain entity — Event
            │   ├── enums/          # EventType
            │   ├── exception/      # DuplicateEventException, EventNotFoundException
            │   ├── gateway/        # Interface EventGateway
            │   └── useCases/       # Interfaces + implementações dos use cases
            └── infrastructure/
                ├── config/         # BeanConfiguration — wiring de DI
                ├── exception/      # GlobalExceptionHandler
                ├── gateway/        # EventRepositoryGateway (implementa EventGateway)
                ├── mapper/         # EventEntityMapper, EventMapper
                ├── persistence/    # EventEntity, EventRepository (JPA)
                ├── presentation/   # EventController
                ├── request/        # EventRequest (inbound DTO)
                └── response/       # ApiResponse, EventResponse (outbound DTOs)
    └── resources/
        └── db/migration/
            │   V1__create_table_event.sql
            │   V2__rename_column_identify_to_identifier.sql
            application.yml
.github/
└── workflows/
    └── ci.yml                      # GitHub Actions — build, test, deploy
```

---

## Tech Stack

| Componente | Tecnologia | Por quê |
|------------|-----------|---------|
| Linguagem | Java 17 LTS | Records, pattern matching, suporte de longo prazo |
| Framework | Spring Boot 3.x | Padrão da indústria, ecossistema de DI poderoso |
| Persistência | Spring Data JPA + Hibernate | Abstração limpa sobre JDBC |
| Banco de Dados | PostgreSQL 15+ | Confiável, comprovado em produção |
| Container | Docker + Docker Compose | Ambientes reproduzíveis |
| Migrations | Flyway | Schema versionado |
| Build | Maven 3.8+ | Lifecycle previsível |
| CI/CD | GitHub Actions | Pipeline automatizado de build, test e deploy |

---

## Roadmap

- [x] Identifier UUID gerado automaticamente
- [ ] Deploy pipeline via GitHub Actions *(em desenvolvimento)*
- [ ] Autenticação com Spring Security + JWT
- [ ] Expandir cobertura de custom exceptions para todos os cenários de erro
- [ ] Paginação e ordenação nos endpoints de listagem
- [ ] Opções adicionais de filtro (intervalo de datas, localização)
- [ ] Testes unitários e de integração

---

## Contribuindo

Issues e PRs são bem-vindos. Se você está adicionando uma feature, comece pelo use case — a lógica de negócio pertence ao `core`, não aos controllers ou implementações de gateway.

## Licença

MIT — veja [LICENSE](./LICENSE).

---

---

<p align="center">
  Desenvolvido por <a href="https://github.com/Andrius-Anselmi">Andrius Anselmi</a> · <a href="https://www.linkedin.com/in/andrius-anselmi">LinkedIn</a>
</p>
