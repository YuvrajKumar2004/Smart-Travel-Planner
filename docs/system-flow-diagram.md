# System Flow Diagram (Text)

1. Client calls `/auth/signup` or `/auth/login`.
2. Backend validates credentials, hashes password (signup), and returns JWT.
3. Client sends JWT in `Authorization: Bearer <token>` for protected APIs.
4. `JwtAuthenticationFilter` validates token and sets authenticated context.
5. User creates trip via `/trips`; owner entry is created in `trip_members`.
6. Members can be added to trip via `/trips/{tripId}/members`.
7. Itinerary items are managed through `/itinerary` endpoints.
8. Expenses are added via `/expenses`; splits are persisted in `expense_splits`.
9. Expense summary endpoint computes per-user balances from expenses and splits.
10. Recommendations are fetched via `/recommendations?destination=...`.
