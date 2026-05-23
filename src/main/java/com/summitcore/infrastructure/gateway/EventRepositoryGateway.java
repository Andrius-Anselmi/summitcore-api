package com.summitcore.infrastructure.gateway;

import com.summitcore.core.entities.Event;
import com.summitcore.core.gateway.EventGateway;
import com.summitcore.infrastructure.mapper.EventEntityMapper;
import com.summitcore.infrastructure.persistence.EventRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventRepositoryGateway implements EventGateway {

    private final EventRepository repository;

    @Override
    public Event create(Event event) {
        return EventEntityMapper.toEvent(repository.save(EventEntityMapper.toEventEntity(event)));
    }
}
