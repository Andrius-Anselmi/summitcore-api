package com.summitcore.core.entities;

import com.summitcore.core.enums.EventType;

import java.time.LocalDateTime;

public record Event(
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

    public static Event create(String name, String description, String identifier, String location,
                               Integer capacity, LocalDateTime startEvent, LocalDateTime endEvent, String organizer,
                               EventType typeEvent){
        return new Event(null, name, description,identifier,location,capacity,startEvent,endEvent,organizer,typeEvent);
    }










}
