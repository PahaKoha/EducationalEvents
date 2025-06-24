CREATE TABLE IF NOT EXISTS user_sphere
(
    user_id uuid NOT NULL,
    sphere_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, sphere_id),
    CONSTRAINT fk_user_sphere_user FOREIGN KEY (user_id)
        REFERENCES "user" (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_user_sphere_sphere FOREIGN KEY (sphere_id)
        REFERENCES sphere (id)
        ON DELETE CASCADE
);

COMMENT ON TABLE user_sphere IS 'Таблица для хранения избранных сфер деятельности пользователей';
COMMENT ON COLUMN user_sphere.user_id IS 'Идентификатор пользователя (внешний ключ)';
COMMENT ON COLUMN user_sphere.sphere_id IS 'Идентификатор сферы деятельности (внешний ключ)';