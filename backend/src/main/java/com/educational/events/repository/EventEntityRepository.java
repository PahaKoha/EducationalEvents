package com.educational.events.repository;

import com.educational.events.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для сущности "Образовательные мероприятие".
 */
@Repository
public interface EventEntityRepository extends JpaRepository<EventEntity, UUID> {

    /**
     * Функция поиска.
     */
    String SEARCH_FUNCTION = "event_search_with_condition_prepare_query";

    /**
     * Поиск по параметрам.
     *
     * @param params параметры поиска.
     * @return список сущностей по фильтру.
     */
    @Query(nativeQuery = true, value = "SELECT * FROM search_entities('" + SEARCH_FUNCTION + "', :params, TRUE, "
        + "CAST(NULL AS event));")
    List<EventEntity> searchEntities(String token, String params);

    /**
     * Поиск по параметрам.
     *
     * @param params параметры поиска.
     * @return количество.
     */
    @Query(nativeQuery = true, value = "SELECT * FROM search_count('" + SEARCH_FUNCTION + "', :params);")
    Long searchCount(String params);
}