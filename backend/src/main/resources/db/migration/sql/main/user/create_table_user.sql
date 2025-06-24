CREATE TABLE IF NOT EXISTS "user"
(
    id uuid PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT uk_user_username UNIQUE (username),
    CONSTRAINT uk_user_email UNIQUE (email)
);

COMMENT ON TABLE "user" IS 'Таблица для хранения информации о пользователях системы';
COMMENT ON COLUMN "user".id IS 'Уникальный идентификатор пользователя (UUID)';
COMMENT ON COLUMN "user".username IS 'Уникальное имя пользователя для входа в систему';
COMMENT ON COLUMN "user".password IS 'Хэшированный пароль пользователя';
COMMENT ON COLUMN "user".email IS 'Электронная почта пользователя (уникальная)';