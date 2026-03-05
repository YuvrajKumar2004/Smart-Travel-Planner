-- Basic schema bootstrap for Smart Travel Planner

CREATE TABLE IF NOT EXISTS users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS trips (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(150) NOT NULL,
  destination VARCHAR(150) NOT NULL,
  start_date DATE,
  end_date DATE,
  created_by BIGINT,
  FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS trip_members (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  trip_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  role VARCHAR(50),
  FOREIGN KEY (trip_id) REFERENCES trips(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS places (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(150) NOT NULL,
  city VARCHAR(100),
  country VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS itineraries (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  trip_id BIGINT NOT NULL,
  day_number INT,
  activity VARCHAR(255),
  FOREIGN KEY (trip_id) REFERENCES trips(id)
);

CREATE TABLE IF NOT EXISTS expenses (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  trip_id BIGINT NOT NULL,
  paid_by BIGINT NOT NULL,
  amount DECIMAL(12,2) NOT NULL,
  description VARCHAR(255),
  FOREIGN KEY (trip_id) REFERENCES trips(id),
  FOREIGN KEY (paid_by) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS expense_splits (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  expense_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  split_amount DECIMAL(12,2) NOT NULL,
  FOREIGN KEY (expense_id) REFERENCES expenses(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS guides (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  destination VARCHAR(150) NOT NULL,
  tips TEXT
);
