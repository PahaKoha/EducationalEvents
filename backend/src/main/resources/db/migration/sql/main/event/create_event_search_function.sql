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
    stmt         varchar;
    page_num     integer;
    page_size    integer;
    sort_stmt    varchar;
    join_sorting text;
BEGIN

    IF countable THEN
        stmt := 'SELECT COUNT(*) FROM event e';
    ELSE
        stmt := 'SELECT
            e.id,
            e.name,
            e.description,
            e.is_internal,
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