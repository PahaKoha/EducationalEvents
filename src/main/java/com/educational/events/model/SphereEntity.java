package com.educational.events.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "sphere")
public class SphereEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
