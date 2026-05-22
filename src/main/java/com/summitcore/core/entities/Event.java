package com.summitcore.core.entities;

import com.summitcore.core.enuns.EventType;

import java.time.LocalDateTime;

public record Event(
         Long id,
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
