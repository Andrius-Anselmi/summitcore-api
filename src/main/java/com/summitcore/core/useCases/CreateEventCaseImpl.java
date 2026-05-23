package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;
import com.summitcore.core.gateway.EventGateway;

public class CreateEventCaseImpl implements CreateEventCase {

    private final EventGateway eventGateway;

    public CreateEventCaseImpl (EventGateway eventGateway){
        this.eventGateway = eventGateway;
    }

    @Override
    public Event execute(Event event) {
        return eventGateway.create(event);
    }
}
