package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;

import java.util.Optional;

public interface FindEventCase {

    Optional<Event> execute(String identify);
}
