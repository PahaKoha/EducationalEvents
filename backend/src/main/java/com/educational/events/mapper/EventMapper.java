package com.educational.events.mapper;

import com.educational.events.model.EventEntity;
import com.educational.events.transfer.EventTo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(
    uses = {
        SphereMapper.class,
        EventTypeMapper.class
    }
)

public abstract class EventMapper {

    public abstract EventTo convert (EventEntity entity);

    public abstract EventEntity convert (EventTo entity);

    public abstract void convertForUpdate (@MappingTarget EventEntity exist, EventTo newData);
}