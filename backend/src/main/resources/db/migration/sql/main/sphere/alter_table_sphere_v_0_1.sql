--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

DO $$
    BEGIN
        IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'sphere') THEN
            IF EXISTS (
                SELECT 1
                FROM information_schema.columns
                WHERE table_name = 'sphere'
                  AND column_name = 'id'
            ) AND EXISTS (
                SELECT 1
                FROM information_schema.columns
                WHERE table_name = 'sphere'
                  AND column_name = 'name'
            ) THEN
                INSERT INTO sphere (id, name)
                VALUES
                    (1, 'Программирование'),
                    (2, 'Искусственный интеллект'),
                    (3, 'Кибербезопасность'),
                    (4, 'Анализ данных'),
                    (5, 'Веб-разработка'),
                    (6, 'Мобильная разработка'),
                    (7, 'Робототехника'),
                    (8, 'Блокчейн'),
                    (9, 'DevOps')
                ON CONFLICT (name) DO NOTHING;

                RAISE NOTICE 'Данные образовательных сфер успешно добавлены';
            ELSE
                RAISE NOTICE 'Таблица sphere существует, но не содержит нужных колонок (id, name)';
            END IF;
        ELSE
            RAISE NOTICE 'Таблица sphere не существует, вставка данных невозможна';
        END IF;
    END $$;