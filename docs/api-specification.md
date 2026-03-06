Base URL
/api/v1

All endpoints are prefixed with /api/v1.

Authentication APIs
Register User
POST /api/v1/auth/signup

Request Body

{
  "name": "Yuvraj Kumar",
  "email": "yuvraj@email.com",
  "password": "password123"
}

Response

{
  "message": "User registered successfully"
}
Login User
POST /api/v1/auth/login

Request Body

{
  "email": "yuvraj@email.com",
  "password": "password123"
}

Response

{
  "token": "jwt_token"
}
User APIs
Get Current User
GET /api/v1/users/me

Returns details of the authenticated user.

Trip APIs
Create Trip
POST /api/v1/trips

Request Body

{
  "tripName": "Varanasi Trip",
  "currentLocation": "Delhi",
  "destination": "Varanasi",
  "days": 3,
  "peopleCount": 4,
  "budget": 15000,
  "tripType": "PILGRIMAGE"
}

Response

{
  "tripId": 101,
  "status": "created"
}
Get Trip Details
GET /api/v1/trips/{tripId}

Returns full trip details including itinerary and members.

Get All Trips of User
GET /api/v1/users/{userId}/trips

Returns all trips created or joined by the user.

Archive Trip
PUT /api/v1/trips/{tripId}/archive

Marks the trip as archived.

Trip Members APIs
Add Member to Trip
POST /api/v1/trips/{tripId}/members

Request

{
  "userId": 5
}
Remove Member
DELETE /api/v1/trips/{tripId}/members/{userId}
Get Trip Members
GET /api/v1/trips/{tripId}/members
Itinerary APIs
Generate Itinerary
POST /api/v1/trips/{tripId}/itinerary/generate

Generates a trip itinerary using the trip planning engine.

Get Itinerary
GET /api/v1/trips/{tripId}/itinerary

Returns the day-wise travel plan.

Update Itinerary (Owner Only)
PUT /api/v1/trips/{tripId}/itinerary

Allows the trip owner to modify the itinerary.

Budget APIs
Budget Feasibility Check
POST /api/v1/trips/budget-check

Request Body

{
  "destination": "Jaipur",
  "days": 3,
  "peopleCount": 2,
  "budget": 10000
}

Response

{
  "minimumBudgetRequired": 14000,
  "budgetSufficient": false
}
Recommendation APIs
Get Destination Recommendations
GET /api/v1/recommendations/destinations?location=Delhi

Returns recommended destinations near the user's location.

Get Places for Destination
GET /api/v1/recommendations/places?destination=Varanasi

Returns attractions or temples for the destination.

Expense APIs
Add Expense
POST /api/v1/trips/{tripId}/expenses

Request

{
  "amount": 1200,
  "paidBy": 3,
  "category": "FOOD",
  "description": "Dinner"
}
Get Expenses
GET /api/v1/trips/{tripId}/expenses

Returns all expenses for the trip.

Split Expense
POST /api/v1/expenses/{expenseId}/split

Splits an expense among trip members.

Get Balance Summary
GET /api/v1/trips/{tripId}/balances

Shows who owes whom.

Guide APIs
Get Guides for a Place
GET /api/v1/guides?place=Varanasi

Returns available local guides for a place.

Weather APIs
Get Weather Information
GET /api/v1/weather?city=Varanasi

Weather data can be fetched from services like
OpenWeather API.

Health Check
Service Health
GET /api/v1/health

Used to check if the backend service is running.

Response Format

All responses follow this structure.

Success

{
  "status": "success",
  "data": {}
}

Error

{
  "status": "error",
  "message": "Invalid request"
}