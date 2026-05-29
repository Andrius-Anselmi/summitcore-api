package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;

import java.util.List;

public interface FindAllEventUseCase {

    List<Event> execute();
}
