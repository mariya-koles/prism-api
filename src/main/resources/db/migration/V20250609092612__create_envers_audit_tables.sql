-- Migration: create_envers_audit_tables
-- Type: V
-- Created at: Mon Jun  9 09:26:12 CDT 2025

-- V2__create_envers_audit_tables.sql
-- Flyway migration to create Envers audit tables for Patient

CREATE TABLE IF NOT EXISTS public.custom_revision_entity (
                                                             id INTEGER NOT NULL,
                                                             "timestamp" BIGINT NOT NULL,
                                                             ip_address VARCHAR(255),
    username VARCHAR(255),
    CONSTRAINT custom_revision_entity_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public.patient_aud (
                                                  id BIGINT NOT NULL,
                                                  rev INTEGER NOT NULL,
                                                  revtype SMALLINT,
                                                  age INTEGER,
                                                  city VARCHAR(50),
    created_at TIMESTAMP(6) WITHOUT TIME ZONE,
    date_of_birth DATE,
    email VARCHAR(100),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    mobile_number VARCHAR(15),
    notes TEXT,
    phone_number VARCHAR(15),
    state VARCHAR(50),
    street_address VARCHAR(100),
    updated_at TIMESTAMP(6) WITHOUT TIME ZONE,
    zipcode VARCHAR(10),
    CONSTRAINT patient_aud_pkey PRIMARY KEY (rev, id)
    );

ALTER TABLE IF EXISTS public.patient_aud
    ADD CONSTRAINT fk_patient_aud_rev FOREIGN KEY (rev)
    REFERENCES public.custom_revision_entity (id)
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;
