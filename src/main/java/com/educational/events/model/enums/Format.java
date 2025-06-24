package com.educational.events.model.enums;

import lombok.Getter;

@Getter
public enum Format {
    ONLINE(1L, "Дистанционный формат"),
    OFFLINE(2L, "Очный формат");

    private final Long id;
    private final String name;

    Format(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
