package com.educational.events.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Фильтр пожертвований СПРС.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(setterPrefix = "with")
@Accessors(chain = true)
public class EventFilter {

    private AdvancedFieldFilter<Long> eventType;
    private AdvancedFieldFilter<Long> sphere;
    private AdvancedFieldFilter<Boolean> isInternal;

}
