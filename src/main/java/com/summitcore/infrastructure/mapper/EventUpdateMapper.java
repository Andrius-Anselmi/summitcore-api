package com.summitcore.infrastructure.mapper;

import com.summitcore.core.entities.Event;
import com.summitcore.infrastructure.persistence.EventEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EventUpdateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(Event request, @MappingTarget EventEntity eventEntity);
}
