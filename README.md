# Smart Travel Planner

## Project Overview
Smart Travel Planner is a collaborative trip planning platform where users can create trips, build itineraries, split expenses, and get place recommendations.

## Features
- User authentication and signup
- Trip creation and member management
- Itinerary planning
- Expense tracking and split management
- Budget feasibility checks
- Recommendation-ready API structure

## System Architecture
- Layered Spring Boot backend (`controller -> service -> repository -> database`)
- DTO-based API contract to avoid exposing entities directly
- SQL schema initialization script for local development

## Tech Stack
- Java
- Spring Boot
- REST APIs
- MySQL / PostgreSQL (configurable)

## Database Schema
High-level schema is documented in `docs/database-schema.md` and bootstrapped in `scripts/database-init.sql`.

## API Endpoints
Sample endpoints:
- `POST /auth/signup`
- `POST /trips`
- `GET /trips/{id}`
- `POST /expenses`

Detailed endpoint documentation is in `docs/api-specification.md`.

## How to Run
1. Install Java 17+ and Maven.
2. Go to `backend`.
3. Run: `mvn spring-boot:run`
4. Initialize DB with `scripts/database-init.sql`.

## Future Improvements
- Frontend web app
- Real recommendation engine integration
- Real-time collaboration features
- Cloud deployment + CI/CD
