package com.educational.events.model;

import com.educational.events.model.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Результат выполнения операции.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@SuperBuilder(setterPrefix = "with")
public class BaseOperationResult {

    /**
     * Статус выполнения операции.
     */
    @Builder.Default
    private OperationStatus status = OperationStatus.OK;

    /**
     * Идентификатор сущности.
     */
    private UUID entityId;

}