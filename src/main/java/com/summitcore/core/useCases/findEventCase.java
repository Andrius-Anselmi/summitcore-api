package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;

public interface findEventCase {

    Event execute(Long id);
}
