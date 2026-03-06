# Database Schema
## Users
id
name
email
password
created_at

## Trips
id
trip_name
creator_id
destination
start_date
days
budget
status

## TripMembers
id
trip_id
user_id

## Places
id
name
city
type
latitude
longitude

## Itinerary
id
trip_id
day_number
place_id
visit_time

## Expenses
id
trip_id
paid_by
amount
category

## ExpenseSplit
id
expense_id
user_id
share_amount
