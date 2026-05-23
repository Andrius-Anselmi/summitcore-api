package com.summitcore.infrastructure.mapper;

import com.summitcore.core.entities.Event;
import com.summitcore.infrastructure.request.EventRequest;
import com.summitcore.infrastructure.response.EventResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EventMapper {

    public static Event toEvent(EventRequest request){
        return Event.create(
                request.name(),
                request.description(),
                request.location(),
                request.identify(),
                request.capacity(),
                request.startEvent(),
                request.endEvent(),
                request.organizer(),
                request.typeEvent()
        );
    }


    public static EventResponse toEventResponse(Event event){
        return EventResponse.builder()
                .id(event.id())
                .name(event.name())
                .description(event.description())
                .location(event.location())
                .identify(event.identify())
                .capacity(event.capacity())
                .startEvent(event.startEvent())
                .endEvent(event.endEvent())
                .organizer(event.organizer())
                .typeEvent(event.typeEvent())
                .build();
    }
}
