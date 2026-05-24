package com.summitcore.infrastructure.config;

import com.summitcore.core.gateway.EventGateway;
import com.summitcore.core.useCases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CreateEventCaseImpl createEventCase(EventGateway eventGateway){
        return new CreateEventCaseImpl(eventGateway);
    }

    @Bean
    public FindAllEventCaseImpl findAllEventCaseImpl(EventGateway eventGateway){
        return new FindAllEventCaseImpl(eventGateway);
    }

    @Bean
    public FindByIdEventCaseImpl findByIdEventCaseImpl(EventGateway eventGateway){
        return new FindByIdEventCaseImpl(eventGateway);
    }
}
