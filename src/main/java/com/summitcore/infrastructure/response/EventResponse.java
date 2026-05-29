package com.summitcore.infrastructure.response;

import com.summitcore.core.enums.EventType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventResponse(
        Long id,
        String name,
        String description,
        String identifier,
        String location,
        Integer capacity,
        LocalDateTime startEvent,
        LocalDateTime endEvent,
        String organizer,
        EventType typeEvent
) {
}
