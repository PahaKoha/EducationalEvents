package com.educational.events.model;

import com.educational.events.model.enums.SortType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сортировка.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Sorting {
    private String parameter;
    private SortType sortType;
}