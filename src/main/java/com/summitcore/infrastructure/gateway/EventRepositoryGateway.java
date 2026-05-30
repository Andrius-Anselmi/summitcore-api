package com.summitcore.infrastructure.gateway;

import com.summitcore.core.entities.Event;
import com.summitcore.core.exception.EventNotFoundException;
import com.summitcore.core.gateway.EventGateway;
import com.summitcore.core.exception.DuplicateEventException;
import com.summitcore.infrastructure.mapper.EventEntityMapper;
import com.summitcore.infrastructure.mapper.EventUpdateMapper;
import com.summitcore.infrastructure.persistence.EventEntity;
import com.summitcore.infrastructure.persistence.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EventRepositoryGateway implements EventGateway {

    private final EventRepository repository;
    private final EventUpdateMapper mapper;


    @Override
    public Event create(Event event) throws DuplicateEventException {
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

    @Override
    public Optional<Event> filterEventByIdentifier(String identify){
        return repository.findEventEntityByIdentifier(identify).map(EventEntityMapper::toEvent);
    }

    @Override
    public void deleteEventById(Long id){
        repository.deleteById(id);
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        EventEntity eventUpdate = repository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found"));
        mapper.updateFromRequest(event, eventUpdate);

        return EventEntityMapper.toEvent(repository.save(eventUpdate));
    }



}
