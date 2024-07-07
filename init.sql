-- Crear usuario 'joaquin' con una contraseña
CREATE USER joaquin WITH PASSWORD 'joaquin';

-- Crear la base de datos 'test_conexa' si no existe
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'test_conexa') THEN
        CREATE DATABASE test_conexa;
    END IF;
END $$;
