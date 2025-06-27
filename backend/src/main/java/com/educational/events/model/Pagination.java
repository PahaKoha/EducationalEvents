package com.educational.events.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Объект пагинации.
 */
@Data
@NoArgsConstructor(staticName = "byDefault")
public class Pagination {

    /**
     * Максимальный размер выгрузки.
     */
    public static final Integer UPLOADING_LIMIT_SIZE = 65536;

    /**
     * Базовый размер для выгрузки отчётов.
     */
    public static final Integer BASE_UPLOADING_SIZE = 10000;

    /**
     * Номер страницы.
     */
    private Integer pageNo = 0;

    /**
     * Размер страницы.
     */
    private Integer pageSize= 50;

    /**
     * Выключена ли пагинация (состояние фильтра, если объект создан с заданным числом записей,
     * что означает, что потребителя не интересует подсчет общего числа записей).
     */
    @JsonIgnore
    @Setter(AccessLevel.PRIVATE)
    private boolean pagingDisabled = false;

    @JsonIgnore
    public Integer getLimit() {
        return pageSize;
    }

    /**
     * Получить смещение.
     *
     * @return смещение.
     */
    @JsonIgnore
    public Long getOffset() {
        if (pageNo == null || pageSize == null) {
            return null;
        }
        return ((long) pageNo) * pageSize;
    }

    /**
     * Настройки пагинации, для получения все сущностей.
     * Актуально при получении дочерних записей какой-либо сущности.
     *
     * @return настроенный объект пагинации.
     */
    public static Pagination getAllRows() {
        var pagination = Pagination.of(0, Integer.MAX_VALUE);
        pagination.setPagingDisabled(true);
        return pagination;
    }

    /**
     * Настройки пагинации, для получения только одной сущность.
     * Актуально при получении одной сущности по идентификатору.
     *
     * @return настроенный объект пагинации.
     */
    public static Pagination getOneRow() {
        var pagination = Pagination.of(0, 1);
        pagination.setPagingDisabled(true);
        return pagination;
    }

    /**
     * Настройки пагинации, для получения первых N сущностей.
     * Актуально при получении N количества сущностей, когда вы знаете, сколько будет записей.
     *
     * @param count - количество записей.
     * @return настроенный объект пагинации.
     */
    public static Pagination getFirst(int count) {
        var pagination = Pagination.of(0, count);
        pagination.setPagingDisabled(true);
        return pagination;
    }

    /**
     * Фабрика.
     *
     * @param pageNo   номер страницы
     * @param pageSize размер странииы
     * @return новый объект пейджинга
     */
    public static Pagination of(Integer pageNo, Integer pageSize) {
        var pagination = new Pagination();
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);
        return pagination;
    }
}