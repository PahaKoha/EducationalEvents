--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

DO $$
    BEGIN
        IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'event_type') THEN
            IF EXISTS (
                SELECT 1 FROM information_schema.columns
                WHERE table_name = 'event_type' AND column_name = 'id'
            ) AND EXISTS (
                SELECT 1 FROM information_schema.columns
                WHERE table_name = 'event_type' AND column_name = 'name'
            ) THEN
                INSERT INTO event_type (id, name) VALUES
                                                      (1, 'Хакатон'),
                                                      (2, 'Лекция'),
                                                      (3, 'Мастер-класс'),
                                                      (4, 'Воркшоп'),
                                                      (5, 'Кейс-чемпионат'),
                                                      (6, 'Вебинар'),
                                                      (7, 'Конференция')
                ON CONFLICT (name) DO NOTHING;

                RAISE NOTICE 'Добавлены типы мероприятий (%)',
                    (SELECT string_agg(name, ', ') FROM event_type WHERE id BETWEEN 1 AND 10);
            ELSE
                RAISE NOTICE 'Таблица event_type существует, но не содержит нужных колонок';
            END IF;
        ELSE
            RAISE NOTICE 'Таблица event_type не существует. Сначала создайте таблицу';
        END IF;
    EXCEPTION WHEN OTHERS THEN
        RAISE EXCEPTION 'Ошибка при заполнении event_type: %', SQLERRM;
    END $$;