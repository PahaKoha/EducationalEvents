package com.educational.events.model;

import com.educational.events.model.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Результат выполнения операции.
 */
@Getter
@Setter
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

    /**
     * Сообщение.
     */
    private String message;

}