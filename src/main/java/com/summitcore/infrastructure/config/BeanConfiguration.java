package com.summitcore.infrastructure.config;

import com.summitcore.core.gateway.EventGateway;
import com.summitcore.core.useCases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CreateEventCase createEventCase(EventGateway eventGateway){
        return new CreateEventCaseImpl(eventGateway);
    }

    @Bean
    public FindAllEventCase findAllEventCase(EventGateway eventGateway){
        return new FindAllEventCaseImpl(eventGateway);
    }

    @Bean
    public FindByIdEventCase findByIdEventCase(EventGateway eventGateway){
        return new FindByIdEventCaseImpl(eventGateway);
    }
}
