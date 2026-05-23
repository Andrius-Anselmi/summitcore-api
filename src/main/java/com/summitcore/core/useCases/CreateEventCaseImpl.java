package com.summitcore.core.useCases;

import com.summitcore.core.entities.Event;

public class createEventCaseImpl implements createEventCase {

    @Override
    public Event execute(Event event) {
        return event;
    }
}
