package com.educational.events.transfer;

import com.educational.events.model.enums.Format;

import java.time.Instant;
import java.util.UUID;

public class EventTo {

    private UUID id;

    private String name;

    private String description;

    private Boolean isInternal;

    private EventTypeTo eventType;

    private SphereTo sphere;

    private Instant startAt;

    private Instant endAt;

    private Integer maxParticipants;

    private String infoLink;

    private Format format;
}
