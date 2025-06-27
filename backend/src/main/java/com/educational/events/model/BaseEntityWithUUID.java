package com.educational.events.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
@MappedSuperclass
@FieldNameConstants
public class BaseEntityWithUUID {

    @Id
    @Column(name = "id")
    protected UUID id;

    public void createAction() {
        setId(UUID.randomUUID());
    }
}
