package com.educational.events.mapper;

import com.educational.events.model.EventTypeEntity;
import com.educational.events.transfer.EventTypeTo;

public abstract class EventTypeMapper {

    public abstract EventTypeTo convert(EventTypeEntity entity);

    public abstract EventTypeEntity convert(EventTypeTo entity);
}
