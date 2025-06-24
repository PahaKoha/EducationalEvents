CREATE TABLE IF NOT EXISTS sphere
(
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT uk_sphere_name UNIQUE (name)
);

COMMENT ON TABLE sphere IS 'Таблица сфер образовательного мероприятия';
COMMENT ON COLUMN sphere.id IS 'Уникальный идентификатор сферы образовательного мероприятия (UUID)';
COMMENT ON COLUMN sphere.name IS 'Наименование сферы образовательного мероприятия (уникальное)';