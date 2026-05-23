package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;

public interface CreateEventCase {

    Event execute(Event event);
}
