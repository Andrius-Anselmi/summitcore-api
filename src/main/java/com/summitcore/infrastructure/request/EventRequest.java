package com.summitcore.infrastructure.request;

import com.summitcore.core.enums.EventType;

import java.time.LocalDateTime;

public record EventRequest(
        String name,
        String description,
        String identify,
        String location,
        Integer capacity,
        LocalDateTime startEvent,
        LocalDateTime endEvent,
        String organizer,
        EventType typeEvent
) {
}
