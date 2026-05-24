package com.summitcore.infrastructure.gateway;

import com.summitcore.core.entities.Event;
import com.summitcore.core.gateway.EventGateway;
import com.summitcore.infrastructure.mapper.EventEntityMapper;
import com.summitcore.infrastructure.persistence.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EventRepositoryGateway implements EventGateway {

    private final EventRepository repository;


    @Override
    public Event create(Event event) {
        return EventEntityMapper.toEvent(repository.save(EventEntityMapper.toEventEntity(event)));
    }

    @Override
    public List<Event> findAll(){
        return repository.findAll().stream().map(EventEntityMapper::toEvent).toList();
    }

    @Override
    public Optional<Event> findById(Long id){
        return repository.findById(id).map(EventEntityMapper::toEvent);
    }
}
