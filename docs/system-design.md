# System Design

## Architecture Style
Layered monolith (REST API):
- `controller` -> HTTP layer
- `service` -> business rules and orchestration
- `repository` -> persistence abstraction
- `model` -> JPA entities
- `dto` -> API contracts
- `exception` -> centralized error handling
- `config` -> security and framework configuration

## Design Principles
- Stateless authentication with JWT
- DTO-first API boundaries (entities are not exposed directly)
- Validation at API boundaries (`jakarta.validation`)
- Transactional service methods for write operations
- Normalized relational schema for integrity and query performance

## Security Design
- Passwords stored as BCrypt hashes
- JWT generated during signup/login
- Protected APIs require `Authorization: Bearer <token>`
- Public routes: `/auth/**`, Swagger docs

## Data Model Highlights
- Trip ownership is modeled by `trips.created_by`
- Membership modeled via `trip_members` join entity
- Shared expense modeled with `expenses` and `expense_splits`
- Balances derived from payer contribution minus split obligations

## Recommendation Engine (Current Version)
- Lightweight rule-based city recommendation map
- Deterministic fallback for unknown destinations
- Can be replaced with external APIs or ML pipeline later
