package com.educational.events.mapper;

import com.educational.events.model.EventTypeEntity;
import com.educational.events.transfer.EventTypeTo;
import org.mapstruct.Mapper;

@Mapper
public abstract class EventTypeMapper {

    public abstract EventTypeTo convert(EventTypeEntity entity);

    public abstract EventTypeEntity convert(EventTypeTo entity);
}
