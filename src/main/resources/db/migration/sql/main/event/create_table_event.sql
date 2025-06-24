CREATE TABLE IF NOT EXISTS event
(
    id uuid PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_internal BOOLEAN DEFAULT TRUE,
    event_type_id BIGINT NOT NULL,
    sphere_id BIGINT NOT NULL,
    start_at TIMESTAMP WITH TIME ZONE NOT NULL,
    end_at TIMESTAMP WITH TIME ZONE NOT NULL,
    max_participants INTEGER,
    info_link VARCHAR(512),
    format VARCHAR(50) NOT NULL,

    CONSTRAINT fk_event_event_type FOREIGN KEY (event_type_id)
        REFERENCES event_type (id)
        ON UPDATE CASCADE,
    CONSTRAINT fk_event_sphere FOREIGN KEY (sphere_id)
        REFERENCES sphere (id)
        ON UPDATE CASCADE,

    CONSTRAINT chk_event_dates CHECK (end_at > start_at),
    CONSTRAINT chk_max_participants CHECK (max_participants > 0 OR max_participants IS NULL)
);

COMMENT ON TABLE event IS 'Таблица для хранения информации об образовательный мероприятиях';
COMMENT ON COLUMN event.id IS 'Уникальный идентификатор события (UUID)';
COMMENT ON COLUMN event.name IS 'Название ОМ';
COMMENT ON COLUMN event.description IS 'Подробное описание события';
COMMENT ON COLUMN event.is_internal IS 'Флаг: является ли ОМ внутренним (true) или публичным (false)';
COMMENT ON COLUMN event.event_type_id IS 'Ссылка на тип ОМ';
COMMENT ON COLUMN event.sphere_id IS 'Ссылка на сферу ОМ';
COMMENT ON COLUMN event.start_at IS 'Дата и время начала события';
COMMENT ON COLUMN event.end_at IS 'Дата и время окончания события';
COMMENT ON COLUMN event.max_participants IS 'Максимальное количество участников';
COMMENT ON COLUMN event.info_link IS 'Ссылка на дополнительную информацию о событии';
COMMENT ON COLUMN event.format IS 'Формат проведения события (оффлайн/онлайн/гибридный)';