package com.educational.events.model;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import com.educational.events.model.enums.AdvancedFieldFilterType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Расширенный фильтр по полю.
 *
 * @param <T> тип значения
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdvancedFieldFilter<T> {

    private AdvancedFieldFilterType type;
    private T singleValue;
    private List<T> multipleValue;

    /**
     * Сеттер значения, предупреждающий об ошибке.
     * @param multipleValue список значений фильтра
     * @return фильтр
     */
    public AdvancedFieldFilter<T> setMultipleValue(List<T> multipleValue) {
        // Если в значениях только null, процедуры будут падать
        if (multipleValue != null && multipleValue.stream().allMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Multiple values must contain at least one non-null element");
        }
        this.multipleValue = multipleValue;
        return this;
    }

    /**
     * Фильтр для поиска по множеству (из одного элемента).
     *
     * @param value единственный элемент множества
     * @param <T>   тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> in(T value) {
        return in(List.of(value));
    }

    /**
     * Фильтр для поиска по множеству (из двух элементов).
     *
     * @param value1 первый элемент множества
     * @param value2 второй элемент множества
     * @param <T>    тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> in(T value1, T value2) {
        return in(List.of(value1, value2));
    }

    /**
     * Фильтр для поиска по множеству (из двух элементов).
     *
     * @param value1 первый элемент множества
     * @param value2 второй элемент множества
     * @param value3 третий элемент множества
     * @param <T>    тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> in(T value1, T value2, T value3) {
        return in(List.of(value1, value2, value3));
    }

    /**
     * Фильтр для поиска по множеству.
     *
     * @param values коллекция значений.
     * @param <T>    тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> in(Collection<T> values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("Cannot create IN filter with empty collection");
        }

        var filter = new AdvancedFieldFilter<T>();
        filter.setMultipleValue(new ArrayList<>(values));
        filter.setType(AdvancedFieldFilterType.IN);
        return filter;
    }

    /**
     * Фильтр для поиска по множеству.
     *
     * @param values коллекция значений.
     * @param <T>    тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> notIn(Collection<T> values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("Cannot create NOT IN filter with empty collection");
        }

        var filter = new AdvancedFieldFilter<T>();
        filter.setMultipleValue(new ArrayList<>(values));
        filter.setType(AdvancedFieldFilterType.NOT_IN);
        return filter;
    }

    /**
     * Фильтр для поиска по множеству или null.
     *
     * @param values коллекция значений.
     * @param <T>    тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> notInOrNull(Collection<T> values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("Cannot create NOT IN filter with empty collection");
        }

        var filter = new AdvancedFieldFilter<T>();
        filter.setMultipleValue(new ArrayList<>(values));
        filter.setType(AdvancedFieldFilterType.NOT_IN_OR_NULL);
        return filter;
    }

    /**
     * Фильтр для поиска по значению.
     *
     * @param value значений.
     * @param <T>   тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> eq(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.EQUALS);
        return filter;
    }

    /**
     * Фильтр для поиска по значению без учета регистра.
     *
     * @param value значений.
     * @param <T>   тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> ieq(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.EQUALS_IGNORE_CASE);
        return filter;
    }

    /**
     * Фильтр для поиска по значению.
     *
     * @param value значение
     * @param <T>   тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> notEq(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.NOT_EQUALS);
        return filter;
    }

    /**
     * Фильтр для поиска по значению <code>true</code>.
     *
     * @return фильтр
     */
    public static AdvancedFieldFilter<Boolean> eqTrue() {
        var filter = new AdvancedFieldFilter<Boolean>();
        filter.setType(AdvancedFieldFilterType.EQUALS);
        filter.setSingleValue(TRUE);
        return filter;
    }

    /**
     * Фильтр для поиска по значению не <code>false</code>.
     *
     * @return фильтр
     */
    public static AdvancedFieldFilter<Boolean> eqFalse() {
        var filter = new AdvancedFieldFilter<Boolean>();
        filter.setType(AdvancedFieldFilterType.EQUALS);
        filter.setSingleValue(FALSE);
        return filter;
    }

    /**
     * Фильтр для поиска по длине значения.
     *
     * @param value значение
     * @param <T>   тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> eqLen(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.EQUALS_LENGTH);
        return filter;
    }

    /**
     * Фильтр для поиска по значению.
     *
     * @param value значений.
     * @param <T>   тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> contains(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.CONTAINS);
        return filter;
    }

    /**
     * Фильтр для поиска по списку значений.
     *
     * @param values значения.
     * @param <T>   тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> contains(Collection<T> values) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setMultipleValue(new ArrayList<>(values));
        filter.setType(AdvancedFieldFilterType.CONTAINS);
        return filter;
    }

    /**
     * Фильтр для поиска по значению <code>null</code>.
     *
     * @param <T> тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> isNull() {
        var filter = new AdvancedFieldFilter<T>();
        filter.setType(AdvancedFieldFilterType.IS_NULL);
        return filter;
    }

    /**
     * Фильтр для поиска по значению не <code>null</code>.
     *
     * @param <T> тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> isNotNull() {
        var filter = new AdvancedFieldFilter<T>();
        filter.setType(AdvancedFieldFilterType.IS_NOT_NULL);
        return filter;
    }

    /**
     * Фильтр для поиска по диапазону между двумя значениями (включительно).
     *
     * @param start начало диапазона
     * @param end   конец диапазона
     * @param <T>   тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> btw(T start, T end) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setType(AdvancedFieldFilterType.BETWEEN);
        filter.setMultipleValue(List.of(start, end));
        return filter;
    }

    /**
     * Фильтр для поиска по значению или null.
     *
     * @param value значение
     * @param <T>   тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> eqOrNull(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.EQUALS_OR_NULL);
        return filter;
    }

    /**
     * Фильтр для поиска больше либо равно.
     *
     * @param value значение
     * @param <T>   тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> grOrEq(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.GREATER_OR_EQUALS);
        return filter;
    }

    /**
     * Фильтр для поиска больше либо равно или null.
     *
     * @param value значение
     * @param <T>   тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> grOrEqOrNull(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.GREATER_OR_EQUALS_OR_NULL);
        return filter;
    }

    /**
     * Фильтр для поиска меньше либо равно.
     *
     * @param value значение
     * @param <T>   тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> lwOrEq(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.LOWER_OR_EQUALS);
        return filter;
    }

    /**
     * Фильтр для поиска меньше.
     *
     * @param value значение
     * @param <T>   тип значения
     * @return фильтр
     */
    public static <T> AdvancedFieldFilter<T> lw(T value) {
        var filter = new AdvancedFieldFilter<T>();
        filter.setSingleValue(value);
        filter.setType(AdvancedFieldFilterType.LOWER);
        return filter;
    }
}