package com.summitcore.core.gateway;

import com.summitcore.core.entities.Event;

import java.util.List;
import java.util.Optional;

public interface EventGateway {

    Event create(Event event);
    List<Event> findAll();
    Optional<Event> findById(Long id);


}
