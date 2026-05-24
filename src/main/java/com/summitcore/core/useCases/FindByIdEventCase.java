package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;

import java.util.Optional;

public interface FindByIdEventCase {

    Optional<Event> execute(Long id);
}
