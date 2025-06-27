package com.educational.events.mapper;

import com.educational.events.model.SphereEntity;
import com.educational.events.transfer.SphereTo;
import org.mapstruct.Mapper;

@Mapper
public abstract class SphereMapper {

    public abstract SphereTo convert (SphereEntity entity);

    public abstract SphereEntity convert (SphereTo entity);
}
