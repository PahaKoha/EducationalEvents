package com.educational.events.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event_type")
public class EventTypeEntity {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    public EventTypeEntity(Long id) {
        this.id = id;
    }
}
