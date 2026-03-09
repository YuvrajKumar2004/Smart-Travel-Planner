# Smart Travel Planner

Smart Travel Planner is a production-style Spring Boot backend for planning group trips, managing itineraries, splitting expenses, and fetching destination recommendations.

## Backend Tech Stack
- Java 17
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- MySQL
- Maven
- OpenAPI (Swagger)
- Lombok

## Folder Structure
```text
backend/
  src/main/java/com/travelplanner/
    config/
      AppConfig.java
      AppUserDetailsService.java
      JwtAuthenticationFilter.java
      SecurityConfig.java
      SwaggerConfig.java
    controller/
      AuthController.java
      TripController.java
      ItineraryController.java
      ExpenseController.java
      RecommendationController.java
    dto/
      ErrorResponse.java
      auth/
      trip/
      itinerary/
      expense/
      recommendation/
    exception/
      BadRequestException.java
      ResourceNotFoundException.java
      UnauthorizedException.java
      GlobalExceptionHandler.java
    model/
      User.java
      Trip.java
      TripMember.java
      ItineraryItem.java
      Expense.java
      ExpenseSplit.java
    repository/
      UserRepository.java
      TripRepository.java
      TripMemberRepository.java
      ItineraryRepository.java
      ExpenseRepository.java
      ExpenseSplitRepository.java
    service/
      AuthService.java
      TripService.java
      ItineraryService.java
      ExpenseService.java
      RecommendationService.java
    util/
      JwtUtil.java
    TravelPlannerApplication.java
  src/main/resources/
    application.properties
  pom.xml

docs/
  api-specification.md
  database-schema.md
  system-design.md
  system-flow-diagram.md

scripts/
  database-init.sql
```

## Core Features
- User signup and login with BCrypt password hashing and JWT token generation
- Trip creation, retrieval, listing by user, deletion, and member management
- Day-wise itinerary CRUD for each trip
- Shared expense tracking with split calculation and user balance summary
- Destination recommendations (attractions, restaurants, hotels)

## API Docs
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## Run Locally
1. Install Java 17+ and Maven.
2. Create MySQL DB (or let URL auto-create): `smart_travel_planner`.
3. Update credentials in `backend/src/main/resources/application.properties`.
4. Set a secure JWT secret (`app.jwt.secret`) with at least 32 characters.
5. Start backend:
```bash
cd backend
mvn spring-boot:run
```

## Security Notes
- All routes except `/auth/**` and Swagger docs require a valid Bearer token.
- Token is returned from `/auth/signup` and `/auth/login`.

## Deliverables Coverage
- Complete layered folder structure
- Normalized database schema
- Entity (model) classes with JPA relationships
- Repository interfaces
- Service layer business logic
- REST controller APIs
- Sample request/response documentation
- Architecture + system flow documentation
