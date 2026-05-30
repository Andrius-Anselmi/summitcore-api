package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;
import com.summitcore.core.exception.EventNotFoundException;
import com.summitcore.core.gateway.EventGateway;

import java.util.Optional;

public class UpdateEventUseCaseImpl implements UpdateEventUseCase {

    private final EventGateway eventGateway;

    public UpdateEventUseCaseImpl(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public Event execute(Long id, Event event) {
        Optional<Event> eventById = eventGateway.findById(id);
        if (eventById.isPresent()) {
            return eventGateway.updateEvent(id, event);
        }
        throw new EventNotFoundException("Not found event");
    }
}
