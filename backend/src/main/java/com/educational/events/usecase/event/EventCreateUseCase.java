package com.educational.events.usecase.event;

import com.educational.events.model.BaseOperationResult;
import com.educational.events.repository.EventEntityRepository;
import com.educational.events.transfer.EventTo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventCreateUseCase {

    @Autowired
    private EventEntityRepository eventEntityRepository;

    public BaseOperationResult exec(EventTo newEvent) {
        return new BaseOperationResult();
    }
}
