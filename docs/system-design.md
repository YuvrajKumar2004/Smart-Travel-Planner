# System Design

## Overview
Smart Travel Planner follows a layered architecture with clear separation of concerns.

## Layers
- Controller: Exposes REST endpoints.
- Service: Implements business logic.
- Repository: Handles persistence.
- Entity: Maps domain tables.
- DTO: API request/response models.

## Non-Functional Goals
- Maintainable service boundaries
- Easy testability of business logic
- Extensible architecture for recommendations and guides
