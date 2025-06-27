--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

DO $$
    BEGIN
        IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'role') THEN
            IF EXISTS (
                SELECT 1
                FROM information_schema.columns
                WHERE table_name = 'role'
                  AND column_name = 'id'
            ) AND EXISTS (
                SELECT 1
                FROM information_schema.columns
                WHERE table_name = 'role'
                  AND column_name = 'name'
            ) THEN
                INSERT INTO role (id, name)
                VALUES
                    (1, 'ROLE_USER'),
                    (2, 'ROLE_ADMIN'),
                    (3, 'ROLE_SUPER_ADMIN')
                ON CONFLICT (name) DO NOTHING;

                RAISE NOTICE 'Данные ролей успешно добавлены';
            ELSE
                RAISE NOTICE 'Таблица role существует, но не имеет нужных колонок';
            END IF;
        ELSE
            RAISE NOTICE 'Таблица role не существует';
        END IF;
    END $$;