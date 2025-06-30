--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

DROP FUNCTION IF EXISTS event_search_with_condition_prepare_query CASCADE;
CREATE FUNCTION event_search_with_condition_prepare_query(countable bool, params json, pagination bool)
    RETURNS text
    LANGUAGE plpgsql
AS
$$
DECLARE
filter       varchar;
    stmt
varchar;
    page_num
integer;
    page_size
integer;
    sort_stmt
varchar;
    join_sorting
text;
BEGIN

    IF
countable THEN
        stmt := 'SELECT COUNT(*) FROM event e';
ELSE
        stmt := 'SELECT
            e.id::varchar,
            e.name,
            e.description,
            e.is_internal,
            et.name as event_type_name,
            s.name as sphere_name,
            e.start_at,
            e.end_at,
            e.max_participants,
            e.info_link,
            e.format
        FROM event e
        LEFT JOIN event_type et ON e.event_type_id = et.id
        LEFT JOIN sphere s ON e.sphere_id = s.id
        WHERE 1=1';

END IF;

RETURN stmt;
END
$$;

DROP TYPE IF EXISTS event_projection CASCADE;
CREATE TYPE event_projection AS (
    id               varchar,
    name             varchar,
    description      text,
    is_internal      boolean,
    event_type_name  varchar,
    sphere_name      varchar,
    start_at         timestamptz,
    end_at           timestamptz,
    max_participants integer,
    info_link        varchar,
    format           varchar
    );