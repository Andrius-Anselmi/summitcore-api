package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;
import com.summitcore.core.gateway.EventGateway;

import java.util.List;

public class FindAllEventCaseImpl implements FindAllEventCase {

    private final EventGateway eventGateway;

    public FindAllEventCaseImpl(EventGateway eventGateway){
        this.eventGateway = eventGateway;
    }

    @Override
    public List<Event> execute(){
        return eventGateway.findAll();
    }
}
