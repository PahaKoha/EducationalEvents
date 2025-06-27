package com.educational.events.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Тип сортировки.
 */
@AllArgsConstructor
@Getter
public enum SortType {

    /**
     * Возрастание с обработкой null'ов по умолчанию.
     */
    ASC("ASC", "NULLS_FIRST"),

    /**
     * Возрастание с обратной обработкой null'ов.
     */
    ASC_ALTERNATE("ASC", "NULLS_LAST"),

    /**
     * Убывание с обработкой null'ов по умолчанию.
     */
    DESC("DESC", "NULLS_LAST"),

    /**
     * Убывание с обратной обработкой null'ов.
     */
    DESC_ALTERNATE("DESC", "NULLS_FIRST");

    private final String directionLiteral;
    private final String nullHandlingLiteral;
}

