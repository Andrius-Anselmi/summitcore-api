package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;
import com.summitcore.core.exception.EventNotFoundException;
import com.summitcore.core.gateway.EventGateway;

public class FindByIdEventUseCaseImpl implements FindByIdEventUseCase {

    private final EventGateway eventGateway;

    public FindByIdEventUseCaseImpl(EventGateway eventGateway){
        this.eventGateway = eventGateway;
    }

    @Override
    public Event execute(Long id) {
        return eventGateway.findById(id)
                .orElseThrow(() -> new EventNotFoundException(
                        "No event found with id: " + id
                ));
    }
}
