-- Migration: create_audit_table
-- Type: V
-- Created at: Sun Jun  8 15:27:03 CDT 2025

CREATE TABLE audit_log (
                           id SERIAL PRIMARY KEY,
                           entity_name VARCHAR(100) NOT NULL,
                           entity_id BIGINT,
                           action VARCHAR(10) NOT NULL, -- CREATE, READ, UPDATE, DELETE
                           changed_field VARCHAR(100),
                           old_value TEXT,
                           new_value TEXT,
                           username VARCHAR(100),
                           ip_address VARCHAR(45), -- Supports IPv4 and IPv6
                           request_id UUID,
                           context JSONB,
                           timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);