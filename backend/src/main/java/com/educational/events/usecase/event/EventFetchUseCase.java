package com.educational.events.usecase.event;

import com.educational.events.model.BaseOperationResult;
import com.educational.events.repository.EventEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventFetchUseCase {

    private final EventEntityRepository eventEntityRepository;

    public BaseOperationResult exec () {
        return new BaseOperationResult();
    }
}
