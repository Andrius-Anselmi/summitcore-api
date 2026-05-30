package com.summitcore.infrastructure.config;

import com.summitcore.core.gateway.EventGateway;
import com.summitcore.core.useCases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CreateEventUseCase createEventCase(EventGateway eventGateway){
        return new CreateEventUseCaseImpl(eventGateway);
    }

    @Bean
    public FindAllEventUseCase findAllEventCase(EventGateway eventGateway){
        return new FindAllEventUseCaseImpl(eventGateway);
    }

    @Bean
    public FindByIdEventUseCase findByIdEventCase(EventGateway eventGateway){
        return new FindByIdEventUseCaseImpl(eventGateway);
    }

    @Bean
    public FilterEventUseCase filterEventCase(EventGateway eventGateway){
        return new FilterEventUseCaseImpl(eventGateway);
    }
    
    @Bean
    public DeleteEventByIdUseCase DeleteEventByIdUseCase(EventGateway eventGateway){
        return new DeleteEventByIdUseCaseImpl(eventGateway);
    }

    @Bean
    public UpdateEventUseCase UpdateEventUseCase(EventGateway eventGateway){
        return new UpdateEventUseCaseImpl(eventGateway);
    }
}
