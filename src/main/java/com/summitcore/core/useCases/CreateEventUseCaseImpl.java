package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;
import com.summitcore.core.exception.DuplicateEventException;
import com.summitcore.core.gateway.EventGateway;

public class CreateEventUseCaseImpl implements CreateEventUseCase {

    private final EventGateway eventGateway;

    public CreateEventUseCaseImpl(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public Event execute(Event event) {

        eventGateway.filterEventByIdentifier(event.identifier()).ifPresent(e -> {
            throw new DuplicateEventException("Identifier " + event.identifier() + " already exists");
            });

        return eventGateway.create(event);
    }
}
