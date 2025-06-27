package com.educational.events.model;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Параметры поиска бизнес-сущностей.
 *
 * @param <F> тип фильтра
 */
@Data
@Log4j2
@Accessors(chain = true)
public class BusinessFetchQueryParams<F> {

    /**
     * Фильтра для поиска бизнес-сущностей.
     */
    private F filter;

    /**
     * Параметры пагинации.
     */
    private Pagination pagination;

    /**
     * Параметры сортировки.
     */
    private List<Sorting> sorting;

    public Pagination getPagination() {
        return Optional.ofNullable(pagination)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "it is forbidden to use query parameters without defining pagination"));
    }

    public List<Sorting> getSorting() {
        return Optional.ofNullable(sorting).orElseGet(ArrayList::new);
    }


    /**
     * Построение параметров с кастомизированным фильтром, указанной пагинацией и сортировкой.
     *
     * @param filter             фильтр
     * @param filterCustomizer   цепочка преобразований фильтра
     * @param paginationSupplier метод получения пагинации
     * @param sorting            сортировка
     * @param <T>                тип фильтра
     * @return параметры
     */
    public static <T> BusinessFetchQueryParams<T> buildWithCustomizableFilter(
        @NonNull T filter,
        @NonNull Consumer<T> filterCustomizer,
        @NonNull Supplier<Pagination> paginationSupplier,
        @Nullable List<Sorting> sorting
    ) {
        filterCustomizer.accept(filter);
        var params = new BusinessFetchQueryParams<T>();
        params.setFilter(filter);
        params.setPagination(paginationSupplier.get());
        params.setSorting(sorting);
        return params;
    }

    /**
     * Построение параметров с кастомизированным фильтром.
     * Значения пагинации и сортировки: Pagination::getAllRows, ArrayList::new
     *
     * @param filter           фильтр
     * @param filterCustomizer цепочка преобразований фильтра
     * @param <T>              тип фильтра
     * @return параметры
     */
    public static <T> BusinessFetchQueryParams<T> buildWithCustomizableFilter(
        @NonNull T filter,
        @NonNull Consumer<T> filterCustomizer
    ) {
        return buildWithCustomizableFilter(filter, filterCustomizer, Pagination::getAllRows, new ArrayList<>());
    }
}
