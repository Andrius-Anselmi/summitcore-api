package com.summitcore.core.useCases;

import com.summitcore.core.exception.EventNotFoundException;
import com.summitcore.core.gateway.EventGateway;

public class DeleteEventByIdUseCaseImpl implements DeleteEventByIdUseCase {

    private final EventGateway eventGateway;

    public DeleteEventByIdUseCaseImpl(EventGateway eventGateway){
        this.eventGateway = eventGateway;
    }

    @Override
    public void execute(Long id){
        eventGateway.findById(id).orElseThrow(() -> new EventNotFoundException("No event found with id: " + id));
        eventGateway.deleteEventById(id);


    }
}
