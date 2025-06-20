-- Create JSONB domain for H2 testing
CREATE DOMAIN IF NOT EXISTS jsonb AS TEXT;

-- Create BLOB domain for H2 testing
CREATE DOMAIN IF NOT EXISTS blob AS BINARY(1024);