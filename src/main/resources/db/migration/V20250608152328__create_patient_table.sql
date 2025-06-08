-- Migration: create_patient_table
-- Created at: Sun Jun  8 15:23:28 CDT 2025

CREATE TABLE patient (
                         id SERIAL PRIMARY KEY,
                         first_name VARCHAR(50) NOT NULL,
                         last_name VARCHAR(50) NOT NULL,
                         age INT CHECK (age >= 0),
                         date_of_birth DATE NOT NULL,
                         phone_number VARCHAR(15),
                         mobile_number VARCHAR(15),
                         email VARCHAR(100) UNIQUE,
                         street_address VARCHAR(100),
                         city VARCHAR(50),
                         state VARCHAR(50),
                         zipcode VARCHAR(10),
                         notes TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);