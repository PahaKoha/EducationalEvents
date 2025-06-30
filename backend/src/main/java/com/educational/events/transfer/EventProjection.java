package com.educational.events.transfer;

import java.time.Instant;

public record EventProjection(
        String id,
        String name,
        String description,
        Boolean isInternal,
        String eventTypeName,
        String sphereName,
        Instant startAt,
        Instant endAt,
        Integer maxParticipants,
        String infoLink,
        String format
) {
}
