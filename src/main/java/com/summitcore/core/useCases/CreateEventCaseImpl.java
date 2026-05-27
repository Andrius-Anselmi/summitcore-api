package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;
import com.summitcore.core.exception.DuplicateEventException;
import com.summitcore.core.gateway.EventGateway;

public class CreateEventCaseImpl implements CreateEventCase {

    private final EventGateway eventGateway;

    public CreateEventCaseImpl(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public Event execute(Event event) throws DuplicateEventException {

        if (!(eventGateway.filterEventByIdentify(event.identify()).isEmpty())) {
            throw new DuplicateEventException("Identify " + event.identify() + " already exists");
        }
        return eventGateway.create(event);
    }
}
