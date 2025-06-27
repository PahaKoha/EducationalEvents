--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

CREATE OR REPLACE FUNCTION rename_dependencies(old_name TEXT, new_name TEXT) RETURNS void AS
$$
DECLARE
    r RECORD;
BEGIN
    FOR r IN
        SELECT tc.table_name, tc.constraint_name
        FROM information_schema.table_constraints tc
        WHERE tc.constraint_type = 'FOREIGN KEY'
          AND tc.constraint_name LIKE '%' || old_name || '%'
        LOOP
            EXECUTE FORMAT('ALTER TABLE %I RENAME CONSTRAINT %I TO %I',
                           r.table_name,
                           r.constraint_name,
                           REPLACE(r.constraint_name, old_name, new_name));
        END LOOP;
END;
$$ LANGUAGE plpgsql;

DO
$$
    BEGIN
        IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'user') THEN
            ALTER TABLE "user" RENAME TO itmo_user;

            IF EXISTS (SELECT 1 FROM pg_sequences WHERE sequencename = 'user_id_seq') THEN
                ALTER SEQUENCE user_id_seq RENAME TO itmo_user_id_seq;
            END IF;

            PERFORM rename_dependencies('user', 'itmo_user');

            RAISE NOTICE 'Таблица user успешно переименована в itmo_user';
        ELSE
            RAISE NOTICE 'Таблица user не существует, переименование не требуется';
        END IF;
    END
$$;