jdbc:postgresql://ep-empty-surf-adsq0sfw-pooler.c-2.us-east-1.aws.neon.tech/bank-app-db?user=neondb_owner&password=npg_w5UGmkIbOg8A&sslmode=require&channelBinding=require

✅ FULL PostgreSQL Setup for User bankapp on Database bankdb

--1️ Login to PostgreSQL
	-> sudo -u postgres psql

--2️ Create the user (SCRAM-SHA-256 compatible)
	-> password_encryption = scram-sha-256
-- Then Run: Create user with a password
	-> CREATE USER bankapp WITH PASSWORD 'LexisNexis2025!';
-- Create the database
	-> CREATE DATABASE bankdb OWNER bankapp;

--3️ Grant FULL privileges on the database
	-> GRANT ALL PRIVILEGES ON DATABASE bankdb TO bankapp;
	
4--️ Switch to the database
	-> \c bankdb

--1️ -> Grant privileges on all tables
-- Grant all privileges on existing tables
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO bankapp;

-- Make sure future tables also inherit privileges
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TABLES TO bankapp;

2 -> Grant privileges on all sequences (needed if you use SERIAL or IDENTITY)
-- Existing sequences
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO bankapp;

-- Future sequences
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON SEQUENCES TO bankapp;

3 -> Grant privileges on all functions

-- Existing functions
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO bankapp;

-- Future functions
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON FUNCTIONS TO bankapp;


4 -> Grant privileges on all triggers
GRANT TRIGGER ON ALL TABLES IN SCHEMA public TO bankapp;

ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT TRIGGER ON TABLES TO bankapp;


5 -> (Optional) Schema-wide grant
GRANT USAGE, CREATE ON SCHEMA public TO bankapp;
