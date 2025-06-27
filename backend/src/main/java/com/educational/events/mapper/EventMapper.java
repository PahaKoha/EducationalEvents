package com.educational.events.mapper;

import com.educational.events.model.EventEntity;
import com.educational.events.transfer.EventTo;
import org.mapstruct.Mapper;

@Mapper(
    uses = {
        SphereMapper.class,
        EventTypeMapper.class
    }
)

public abstract class EventMapper {

    public abstract EventTo convert (EventEntity entity);

    public abstract EventEntity convert (EventTo entity);
}