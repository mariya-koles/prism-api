-- Migration: create_consultation_tables
-- Type: V
-- Created at: Tue Jun 10 13:36:53 CDT 2025

CREATE TYPE consultation_type AS ENUM (
    'NUTRITIONAL',
    'WELLNESS',
    'DIABETES',
    'BLOOD_PRESSURE',
    'CARDIAC',
    'MENTAL_HEALTH',
    'MEDICATION_MANAGEMENT',
    'ALLERGY_MANAGEMENT',
    'IMMUNIZATION',
    'THYROID',
    'WEIGHT_MANAGEMENT',
    'STRESS_MANAGEMENT',
    'SLEEP_HEALTH',
    'PHYSICAL_FITNESS',
    'WOMENS_HEALTH',
    'MENS_HEALTH'
);

CREATE TABLE consultation (
                              id BIGSERIAL PRIMARY KEY,
                              patient_id BIGINT NOT NULL REFERENCES patient(id),
                              consultation_type consultation_type NOT NULL,
                              date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              weight_kg NUMERIC(5,2),
                              blood_pressure VARCHAR(15),
                              notes TEXT,
                              type_specific_data JSONB,
                              appointment_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE document (
                                       id BIGSERIAL PRIMARY KEY,
                                       consultation_id BIGINT NOT NULL REFERENCES consultation(id) ON DELETE CASCADE,
                                       filename VARCHAR(255) NOT NULL,
                                       content BYTEA NOT NULL,
                                       mime_type VARCHAR(100),
                                       uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
