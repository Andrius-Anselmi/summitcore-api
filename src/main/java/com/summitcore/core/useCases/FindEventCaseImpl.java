package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;
import com.summitcore.core.gateway.EventGateway;

import java.util.Optional;

public class FindEventCaseImpl implements FindEventCase {

    private final EventGateway eventGateway;

    public FindEventCaseImpl(EventGateway eventGateway){
        this.eventGateway = eventGateway;
    }

@Override
    public Optional<Event> execute(String identify){
        return eventGateway.findEventByIdentify(identify);
    }

}
