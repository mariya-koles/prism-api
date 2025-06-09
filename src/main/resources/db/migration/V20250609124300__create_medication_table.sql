-- Migration: create_medication_table
-- Type: V
-- Created at: Mon Jun  9 12:43:00 CDT 2025

CREATE TABLE medication (
                            id SERIAL PRIMARY KEY,
                            proprietary_name VARCHAR(255) NOT NULL,
                            ndc_package_code VARCHAR(50) NOT NULL,
                            strength VARCHAR(50),
                            dosage_form VARCHAR(100),
                            route VARCHAR(50),
                            monograph_id VARCHAR(50),
                            labeler_name VARCHAR(255),
                            product_ndc VARCHAR(50),
                            non_proprietary_name VARCHAR(255),
                            substance_name VARCHAR(255),
                            product_type_name VARCHAR(100)
);
