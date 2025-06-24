CREATE TABLE IF NOT EXISTS user_event_type
(
    user_id uuid NOT NULL,
    event_type_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, event_type_id),
    CONSTRAINT fk_user_event_type_user FOREIGN KEY (user_id)
        REFERENCES "user" (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_user_event_type_event_type FOREIGN KEY (event_type_id)
        REFERENCES event_type (id)
        ON DELETE CASCADE
);

COMMENT ON TABLE user_event_type IS 'Таблица для хранения избранных типов событий пользователей';
COMMENT ON COLUMN user_event_type.user_id IS 'Идентификатор пользователя (внешний ключ)';
COMMENT ON COLUMN user_event_type.event_type_id IS 'Идентификатор типа события (внешний ключ)';