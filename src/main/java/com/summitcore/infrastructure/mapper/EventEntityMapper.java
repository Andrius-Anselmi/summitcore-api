package com.summitcore.infrastructure.mapper;
import com.summitcore.core.entities.Event;
import com.summitcore.infrastructure.persistence.EventEntity;
import lombok.experimental.UtilityClass;


@UtilityClass
public class EventEntityMapper {

    public static Event toEvent(EventEntity event){
        return new Event(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getIdentifier(),
                event.getLocation(),
                event.getCapacity(),
                event.getStartEvent(),
                event.getEndEvent(),
                event.getOrganizer(),
                event.getTypeEvent()
        );
    }

    public static EventEntity toEventEntity(Event event){
        return EventEntity.builder()
                .id(event.id())
                .name(event.name())
                .description(event.description())
                .location(event.location())
                .identifier(event.identifier())
                .capacity(event.capacity())
                .startEvent(event.startEvent())
                .endEvent(event.endEvent())
                .organizer(event.organizer())
                .typeEvent(event.typeEvent())
                .build();
    }
}
