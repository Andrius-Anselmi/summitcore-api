package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;

public interface UpdateEventUseCase {

    Event execute(Long id , Event event);
}
