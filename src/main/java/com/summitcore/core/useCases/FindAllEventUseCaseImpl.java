package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;
import com.summitcore.core.gateway.EventGateway;

import java.util.List;

public class FindAllEventUseCaseImpl implements FindAllEventUseCase {

    private final EventGateway eventGateway;

    public FindAllEventUseCaseImpl(EventGateway eventGateway){
        this.eventGateway = eventGateway;
    }

    @Override
    public List<Event> execute(){
        return eventGateway.findAll();
    }
}
