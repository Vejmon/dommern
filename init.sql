SELECT 'CREATE DATABASE dommern'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'dommern')\gexec
\c dommern

CREATE USER dommern_api_flyway WITH PASSWORD 'password';
GRANT ALL ON SCHEMA public TO dommern_api_flyway;

CREATE USER dommern_api_user WITH PASSWORD 'password';
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public to dommern_api_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public to dommern_api_user;
ALTER DEFAULT PRIVILEGES
    FOR USER dommern_api_flyway
    IN SCHEMA public
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES
    TO dommern_api_user;
