# Database Schema (Normalized)

## Tables

### `users`
- `id` BIGINT PK AUTO_INCREMENT
- `full_name` VARCHAR(100) NOT NULL
- `email` VARCHAR(150) NOT NULL UNIQUE
- `password_hash` VARCHAR(255) NOT NULL
- `created_at` TIMESTAMP NOT NULL

### `trips`
- `id` BIGINT PK AUTO_INCREMENT
- `name` VARCHAR(150) NOT NULL
- `destination` VARCHAR(150) NOT NULL
- `start_date` DATE NOT NULL
- `end_date` DATE NOT NULL
- `created_by` BIGINT NOT NULL FK -> `users.id`
- `created_at` TIMESTAMP NOT NULL

### `trip_members`
- `id` BIGINT PK AUTO_INCREMENT
- `trip_id` BIGINT NOT NULL FK -> `trips.id`
- `user_id` BIGINT NOT NULL FK -> `users.id`
- `role` VARCHAR(30) NOT NULL
- `joined_at` TIMESTAMP NOT NULL
- Unique key: (`trip_id`, `user_id`)

### `itinerary_items`
- `id` BIGINT PK AUTO_INCREMENT
- `trip_id` BIGINT NOT NULL FK -> `trips.id`
- `day_number` INT NOT NULL
- `activity` VARCHAR(255) NOT NULL
- `location` VARCHAR(255) NOT NULL
- `start_time` TIME NOT NULL
- `end_time` TIME NOT NULL

### `expenses`
- `id` BIGINT PK AUTO_INCREMENT
- `trip_id` BIGINT NOT NULL FK -> `trips.id`
- `paid_by` BIGINT NOT NULL FK -> `users.id`
- `amount` DECIMAL(12,2) NOT NULL
- `description` VARCHAR(255) NOT NULL
- `date` DATE NOT NULL

### `expense_splits`
- `id` BIGINT PK AUTO_INCREMENT
- `expense_id` BIGINT NOT NULL FK -> `expenses.id`
- `user_id` BIGINT NOT NULL FK -> `users.id`
- `share_amount` DECIMAL(12,2) NOT NULL

## Relationship Summary
- User -> Trips: one-to-many (`trips.created_by`)
- User <-> Trip: many-to-many through `trip_members`
- Trip -> ItineraryItems: one-to-many
- Trip -> Expenses: one-to-many
- Expense -> ExpenseSplits: one-to-many
- User -> ExpenseSplits: one-to-many
