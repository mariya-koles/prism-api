-- Migration: create_allergy_table
-- Type: V
-- Created at: Tue Jun 10 10:24:01 CDT 2025

CREATE TABLE allergy (
                         id BIGSERIAL PRIMARY KEY,
                         allergy_type VARCHAR(50) NOT NULL CHECK (allergy_type IN ('Drug', 'Non-Drug')),
                         allergy_name VARCHAR(255),
                         drug_name VARCHAR(255),
                         CHECK (
                             (allergy_type = 'Drug' AND drug_name IS NOT NULL AND allergy_name IS NULL) OR
                             (allergy_type = 'Non-Drug' AND allergy_name IS NOT NULL AND drug_name IS NULL)
                             )
);
