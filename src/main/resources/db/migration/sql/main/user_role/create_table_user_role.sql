CREATE TABLE IF NOT EXISTS user_role
(
    user_id uuid NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id)
        REFERENCES "user" (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id)
        REFERENCES role (id)
        ON DELETE CASCADE
);

COMMENT ON TABLE user_role IS 'Таблица связи многие-ко-многим между пользователями и ролями';
COMMENT ON COLUMN user_role.user_id IS 'Идентификатор пользователя (внешний ключ)';
COMMENT ON COLUMN user_role.role_id IS 'Идентификатор роли (внешний ключ)';