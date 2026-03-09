# API Specification

Base URL: `/`
Authentication: Bearer token for protected endpoints.

## 1. User Authentication

### POST `/auth/signup`
Request:
```json
{
  "fullName": "Aarav Mehta",
  "email": "aarav@example.com",
  "password": "StrongPass123"
}
```
Response:
```json
{
  "userId": 1,
  "fullName": "Aarav Mehta",
  "email": "aarav@example.com",
  "token": "<jwt-token>",
  "tokenType": "Bearer"
}
```

### POST `/auth/login`
Request:
```json
{
  "email": "aarav@example.com",
  "password": "StrongPass123"
}
```
Response: same structure as signup.

## 2. Trip Management

### POST `/trips`
Request:
```json
{
  "name": "Goa Escape",
  "destination": "Goa",
  "startDate": "2026-06-20",
  "endDate": "2026-06-25",
  "createdBy": 1
}
```
Response:
```json
{
  "id": 10,
  "name": "Goa Escape",
  "destination": "Goa",
  "startDate": "2026-06-20",
  "endDate": "2026-06-25",
  "createdBy": 1,
  "members": [
    {
      "userId": 1,
      "fullName": "Aarav Mehta",
      "email": "aarav@example.com",
      "role": "OWNER"
    }
  ]
}
```

### GET `/trips/{id}`
Returns trip details with members.

### GET `/users/{userId}/trips`
Returns all trips where the user is a member.

### DELETE `/trips/{id}`
Response: `204 No Content`

### POST `/trips/{tripId}/members`
Request:
```json
{
  "userId": 2,
  "role": "MEMBER"
}
```

## 3. Itinerary Management

### POST `/itinerary`
Request:
```json
{
  "tripId": 10,
  "dayNumber": 1,
  "activity": "Beach Visit",
  "location": "Baga Beach",
  "startTime": "10:00:00",
  "endTime": "12:00:00"
}
```

### GET `/itinerary/{tripId}`
Returns all itinerary items for the trip.

### PUT `/itinerary/{id}`
Updates day/activity/location/time.

### DELETE `/itinerary/{id}`
Response: `204 No Content`

## 4. Expense Management

### POST `/expenses`
Request:
```json
{
  "tripId": 10,
  "paidBy": 1,
  "amount": 1200.00,
  "description": "Dinner",
  "date": "2026-06-20",
  "splitAmongUserIds": [1, 2]
}
```

### GET `/expenses/{tripId}`
Response includes `expenses[]` and computed `balances[]`.

## 5. Recommendations

### GET `/recommendations?destination={city}`
Example:
`/recommendations?destination=goa`

Response:
```json
{
  "destination": "Goa",
  "attractions": ["Dudhsagar Falls", "Basilica of Bom Jesus", "Baga Beach"],
  "restaurants": ["Gunpowder", "Pousada by the Beach", "Mum's Kitchen"],
  "hotels": ["Taj Exotica", "W Goa", "Alila Diwa Goa"]
}
```

## Error Response Format
```json
{
  "timestamp": "2026-03-09T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/trips"
}
```
