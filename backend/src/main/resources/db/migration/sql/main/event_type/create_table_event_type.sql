CREATE TABLE IF NOT EXISTS event_type
(
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT uk_event_type_name UNIQUE (name)
);

COMMENT ON TABLE event_type IS 'Таблица типов образовательных мероприятий';
COMMENT ON COLUMN event_type.id IS 'Уникальный идентификатор типа образовательного мероприятия (UUID)';
COMMENT ON COLUMN event_type.name IS 'Наименование типа образовательного мероприятия (уникальное)';