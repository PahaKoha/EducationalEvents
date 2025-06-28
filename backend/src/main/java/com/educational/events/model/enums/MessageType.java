package com.educational.events.model.enums;

import lombok.Getter;

@Getter
public enum MessageType {

    REGISTRATION_USER(1L, "registration"),
    NEW_EVENT(2L, "new_event");

    private final Long id;
    private final String name;

    MessageType(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
