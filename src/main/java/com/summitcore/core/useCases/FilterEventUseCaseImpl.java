package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;
import com.summitcore.core.exception.EventNotFoundException;
import com.summitcore.core.gateway.EventGateway;


public class FilterEventUseCaseImpl implements FilterEventUseCase {

    private final EventGateway eventGateway;

    public FilterEventUseCaseImpl(EventGateway eventGateway){
        this.eventGateway = eventGateway;
    }

@Override
    public Event execute(String identifier){
        return eventGateway.filterEventByIdentifier(identifier)
                .orElseThrow(() -> new EventNotFoundException("No event found with identifier: " + identifier));
    }

}
