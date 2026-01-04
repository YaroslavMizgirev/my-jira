-- ***********************************************************************************
--                    INSERT ADMINISTRATOR USER
-- ***********************************************************************************
-- SHA-256 hash of "Administrator": 
-- You can generate it using online tools or PostgreSQL:
-- SELECT encode(digest('Administrator', 'sha256'), 'hex');

BEGIN;

INSERT INTO public.users (username, email, password_hash) VALUES
('Administrator', 'admin@myjira.local', '2c26b46911185131006ba5e4395b92e5b4cc937e8a85744b98e06b28b2e327c0');

COMMIT;
