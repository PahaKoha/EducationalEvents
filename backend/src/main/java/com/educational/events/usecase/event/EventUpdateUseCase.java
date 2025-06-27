package com.educational.events.usecase.event;

import com.educational.events.mapper.EventMapper;
import com.educational.events.model.BaseOperationResult;
import com.educational.events.model.enums.OperationStatus;
import com.educational.events.repository.EventEntityRepository;
import com.educational.events.transfer.EventTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventUpdateUseCase {

    public final EventMapper eventMapper;
    public final EventEntityRepository eventEntityRepository;

    /**
     * Главный метод.
     *
     * @param newData - новое событие.
     * @return - статус операции.
     */
    public BaseOperationResult exec(EventTo newData) {

        var existed = eventEntityRepository.findById(newData.getId());
        if (existed.isEmpty()) {
            return BaseOperationResult
                .builder()
                .withStatus(OperationStatus.FAILED)
                .withMessage("Event with id: " + newData.getId() + " does not exits")
                .build();
        }
        eventMapper.convertForUpdate(existed.get(), newData);
        eventEntityRepository.save(existed.get());

        return BaseOperationResult
            .builder()
            .withStatus(OperationStatus.OK)
            .withEntityId(newData.getId())
            .withMessage("Success event creation!")
            .build();
    }
}
