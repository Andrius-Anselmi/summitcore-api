<div align="center">

# SummitCore
### A production-grade REST API for event management.
### Built with Clean Architecture — use cases isolated, domain protected, zero infrastructure leakage.
&nbsp;

[![Java](https://img.shields.io/badge/java-17_LTS-ED8B00?style=flat-square&labelColor=0a0e14&logo=openjdk&logoColor=ED8B00)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/spring_boot-3.x-6DB33F?style=flat-square&labelColor=0a0e14&logo=spring&logoColor=6DB33F)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/postgresql-15+-4169E1?style=flat-square&labelColor=0a0e14&logo=postgresql&logoColor=4169E1)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/docker-compose-2496ED?style=flat-square&labelColor=0a0e14&logo=docker&logoColor=2496ED)](https://www.docker.com/)
[![Flyway](https://img.shields.io/badge/flyway-migrations-CC0200?style=flat-square&labelColor=0a0e14&logo=flyway&logoColor=white)](https://flywaydb.org/)
[![Maven](https://img.shields.io/badge/maven-3.8+-C71A36?style=flat-square&labelColor=0a0e14&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![CI/CD](https://img.shields.io/badge/github_actions-CI%2FCD-2088FF?style=flat-square&labelColor=0a0e14&logo=githubactions&logoColor=2088FF)](https://github.com/features/actions)
[![Status](https://img.shields.io/badge/status-active_development-F0A500?style=flat-square&labelColor=0a0e14)](.)
[![License](https://img.shields.io/badge/license-MIT-b0e8ff?style=flat-square&labelColor=0a0e14)](./LICENSE)

&nbsp;

🌐 **Language / Idioma:** English · [Português](./README.pt.md)

&nbsp;

[Quick Start](#quick-start) · [Architecture](#architecture) · [API Reference](#api-reference) · [Design Decisions](#design-decisions) · [Roadmap](#roadmap)

&nbsp;

| | | |
|---|---|---|
| **Clean Architecture** strictly enforced | **Use Cases** as first-class citizens | **12-Factor** compliant config |
| **Zero** domain model leakage | **Custom exceptions** & global handler | **Flyway** version-controlled schema |
| **Auto-generated** identifiers | **GitHub Actions** CI/CD | **Docker** deploy-ready |

</div>

---

## The Problem

Building an event API is straightforward. Building one that doesn't collapse under its own weight is not.

Most implementations wire the controller directly to the repository, serialize JPA entities as JSON, and call it done. That works until a schema change breaks the API contract, until business rules get scattered across controllers and services with no clear boundary, until the database layer starts dictating what the domain can express.

SummitCore is built around the decisions that prevent those failures: Clean Architecture with a strict boundary between core and infrastructure, use cases as the single point of business logic, DTOs that decouple the API contract from persistence, centralized exception handling, Flyway for schema migrations, and environment-variable-driven config.

---

## Quick Start

**Prerequisites:** JDK 17+, Maven 3.8+, Docker

```bash
git clone https://github.com/your-username/summitcore-api.git
cd summitcore-api
```

Start PostgreSQL via Docker:

```bash
docker compose up -d
```

Set environment variables (or configure your `.env`):

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/summitcore
export SPRING_DATASOURCE_USERNAME=your_username
export SPRING_DATASOURCE_PASSWORD=your_password
```

Build and run:

```bash
mvn clean install
mvn spring-boot:run
```

API available at `http://localhost:8080`. No manual schema setup needed — Flyway runs migrations automatically on startup.

---

## Architecture

Clean Architecture. The domain knows nothing about Spring, JPA, or PostgreSQL. Infrastructure adapts to the domain — never the other way around.

```
  HTTP Request
       │
       ▼
┌─────────────────────────────────────────────┐
│  Presentation Layer                         │
│  EventController                            │
│  Handles HTTP, routes, status codes         │
│  Returns DTOs — never domain models         │
└────────────────────┬────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────┐
│  Core — Use Cases                           │
│  CreateEventCase / FindAllEventCase         │
│  FindByIdEventCase / FilterEventCase        │
│  DeleteEventByIdCase / UpdateEventCase      │
│  All business rules live here               │
│  Depends on Gateway interfaces only         │
└────────────────────┬────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────┐
│  Core — Gateway (interface)                 │
│  EventGateway                               │
│  Boundary between domain and infrastructure │
└────────────────────┬────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────┐
│  Infrastructure — Gateway (impl)            │
│  EventRepositoryGateway                     │
│  Implements EventGateway via JPA            │
│  Uses EventEntityMapper for conversion      │
└────────────────────┬────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────┐
│  PostgreSQL 15+ (Docker)                    │
│  Schema managed by Flyway migrations        │
│  Never exposed to the domain directly       │
└─────────────────────────────────────────────┘
```

### The Core / Infrastructure split

The `core` package is pure domain — entities, enums, use case interfaces and implementations, and the `EventGateway` interface. It has zero dependency on Spring Data, JPA, or any infrastructure concern.

The `infrastructure` package is where Spring lives — `EventRepositoryGateway` implements `EventGateway`, `EventRepository` talks to PostgreSQL, mappers convert between domain entities and JPA entities, and `BeanConfiguration` wires everything together.

---

## API Reference

### Events

| Method | Route                          | Description | Status         |
|--------|--------------------------------|-------------|----------------|
| `GET` | `api/events`                   | List all events | `200`          |
| `GET` | `api/events/{id}`              | Get event by ID | `200` / `404`  |
| `GET` | `api/events/filter/identifier` | Filter events by identifier | `200` / `404`  |
| `POST` | `api/events`                   | Create a new event | `201` / `409`  |
| `PUT` | `api/events/{id}`              | Update an event | `200` / `404`  |
| `DELETE` | `api/events/{id}`              | Delete an event by ID | `204` / `404`  |

---

## Example Requests

**Creating an event:**

```bash
curl -X POST http://localhost:8080/events \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Spring Boot Workshop",
    "description": "Hands-on session covering Spring Boot 3 and Clean Architecture.",
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
    "description": "Hands-on session covering Spring Boot 3 and Clean Architecture.",
    "date": "2026-07-15",
    "type": "WORKSHOP"
  }
}
```

**Updating an event:**

```bash
curl -X PUT http://localhost:8080/events/b3f1c2d4-e5a6-7890-abcd-ef1234567890 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Spring Boot Workshop — Advanced",
    "description": "Deep dive into Spring Boot 3, Clean Architecture, and testing strategies.",
    "date": "2026-07-20",
    "type": "WORKSHOP"
  }'
```

```json
{
  "success": true,
  "data": {
    "identifier": "b3f1c2d4-e5a6-7890-abcd-ef1234567890",
    "name": "Spring Boot Workshop — Advanced",
    "description": "Deep dive into Spring Boot 3, Clean Architecture, and testing strategies.",
    "date": "2026-07-20",
    "type": "WORKSHOP"
  }
}
```

> All responses are wrapped in `ApiResponse` — a consistent envelope with a `success` flag and a `data` field. The `identifier` is a UUID generated automatically by the domain on creation — the client never needs to supply one.

---

## Exception Handling

Errors are handled globally via `GlobalExceptionHandler`. No try/catch blocks scattered across controllers.

| Exception | HTTP Status | When |
|-----------|-------------|------|
| `DuplicateEventException` | `409 Conflict` | Event with same data already exists |
| `EventNotFoundException` | `404 Not Found` | Resource not found by identifier |

All error responses follow the same `ApiResponse` envelope as successful ones — predictable shape across the entire API.

---

## Design Decisions

Every pattern here is a deliberate choice, not boilerplate.

**Clean Architecture** — The domain sits at the center and depends on nothing. Infrastructure adapts to it. This means the database, the framework, and the HTTP layer can all change without touching a single use case.

**Use Cases as interfaces + implementations** — `CreateEventCase` is an interface; `CreateEventCaseImpl` is its implementation. Use cases are injected by interface, keeping callers decoupled from the concrete logic. Swapping implementations or mocking in tests requires zero refactoring.

**EventGateway as a boundary** — The domain never calls a repository directly. It calls `EventGateway`, an interface it owns. `EventRepositoryGateway` in the infrastructure layer implements that interface. The direction of dependency always points inward.

**Auto-generated identifier** — The `identifier` (UUID) is generated by the domain layer on creation, not delegated to the database sequence. This keeps the domain in control of its own identity — no infrastructure concern bleeds into the core.

**Dual mapper pattern** — `EventMapper` converts between domain entities and DTOs for the presentation layer. `EventEntityMapper` converts between domain entities and JPA entities for the persistence layer. Each mapper has a single, clear responsibility.

**ApiResponse envelope** — All responses — success and error — share the same wrapper. Clients always know the shape they'll receive, regardless of the endpoint.

**EventType enum** — Event types are not free strings in the database. They're a first-class enum in the domain, enforced at the type level before anything reaches persistence.

**Global exception handling** — `@RestControllerAdvice` intercepts typed exceptions and maps them to HTTP responses. Controllers stay clean. Error format is consistent across the entire API.

**Flyway migrations** — Schema changes are version-controlled SQL files. Every environment runs the exact same migrations in the exact same order. No schema drift between machines.

**12-Factor config** — Database credentials come from environment variables. The same artifact runs in any environment without modification.

**GitHub Actions CI/CD** — Build, test, and deploy are automated via workflow files. Every push to `main` triggers the pipeline — no manual steps, no environment-specific surprises.

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

Schema is version-controlled via Flyway. `V1__create_table_event.sql` defines the initial structure. `V2__rename_column_identify_to_identifier.sql` corrects the column name. Source of truth is always the migration chain — not Hibernate's `ddl-auto`.

---

## Project Structure

```
src/
└── main/
    └── java/
        └── com/summitcore/
            ├── core/
            │   ├── entities/       # Domain entity — Event
            │   ├── enums/          # EventType
            │   ├── exception/      # DuplicateEventException, EventNotFoundException
            │   ├── gateway/        # EventGateway interface
            │   └── useCases/       # Use case interfaces + implementations
            └── infrastructure/
                ├── config/         # BeanConfiguration — DI wiring
                ├── exception/      # GlobalExceptionHandler
                ├── gateway/        # EventRepositoryGateway (implements EventGateway)
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

| Component | Technology | Why |
|-----------|-----------|-----|
| Language | Java 17 LTS | Records, pattern matching, long-term support |
| Framework | Spring Boot 3.x | Industry standard, powerful DI ecosystem |
| Persistence | Spring Data JPA + Hibernate | Clean abstraction over JDBC |
| Database | PostgreSQL 15+ | Reliable, production-proven |
| Container | Docker + Docker Compose | Reproducible environments |
| Migrations | Flyway | Version-controlled schema |
| Build | Maven 3.8+ | Predictable lifecycle |
| CI/CD | GitHub Actions | Automated build, test, and deploy pipeline |

---

## Roadmap

- [x] Auto-generated UUID identifier
- [ ] Deploy pipeline via GitHub Actions *(in development)*
- [ ] Authentication with Spring Security + JWT
- [ ] Expand custom exception coverage across all error scenarios
- [ ] Pagination and sorting on list endpoints
- [ ] Additional filter options (date range, location)
- [ ] Unit and integration tests

---

## Contributing

Issues and PRs welcome. If you're adding a feature, start from the use case — business logic belongs in `core`, not in controllers or gateway implementations.

## License

MIT — see [LICENSE](./LICENSE).

---

<div align="center">
Built by [Andrius Anselmi](https://github.com/Andrius-Anselmi) · [LinkedIn](https://www.linkedin.com/in/andrius-anselmi)
</div>