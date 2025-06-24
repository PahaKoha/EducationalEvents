CREATE TABLE IF NOT EXISTS role
(
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT uk_role_name UNIQUE (name)
);

COMMENT ON TABLE role IS 'Таблица ролей пользователей системы';
COMMENT ON COLUMN role.id IS 'Уникальный идентификатор роли (UUID)';
COMMENT ON COLUMN role.name IS 'Наименование роли (уникальное)';