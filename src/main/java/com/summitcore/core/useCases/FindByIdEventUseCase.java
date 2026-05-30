package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;

public interface FindByIdEventUseCase {

    Event execute(Long id);
}
