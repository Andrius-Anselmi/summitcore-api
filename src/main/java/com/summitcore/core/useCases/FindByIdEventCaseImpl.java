package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;
import com.summitcore.core.gateway.EventGateway;

import java.util.Optional;

public class FindByIdEventCaseImpl implements FindByIdEventCase {

    private final EventGateway eventGateway;

    public FindByIdEventCaseImpl(EventGateway eventGateway){
        this.eventGateway = eventGateway;
    }

    @Override
    public Optional<Event> execute(Long id){
        return eventGateway.findById(id);
    }
}
