package com.educational.events.usecase.event;

import com.educational.events.config.RabbitConfig;
import com.educational.events.mapper.EventMapper;
import com.educational.events.model.BaseOperationResult;
import com.educational.events.model.ITMOUser;
import com.educational.events.model.enums.OperationStatus;
import com.educational.events.repository.EventEntityRepository;
import com.educational.events.repository.UserRepository;
import com.educational.events.transfer.EventTo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Компонент для создания события.
 */
@Component
@RequiredArgsConstructor
public class EventCreateUseCase {

    private final EventMapper eventMapper;
    private final EventEntityRepository eventEntityRepository;
    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Главный метод.
     *
     * @param newEvent - новое событие.
     * @return - статус операции.
     */
    public BaseOperationResult exec(EventTo newEvent) {

        var eventEntity = eventMapper.convert(newEvent);
        eventEntity.setId(UUID.randomUUID());
        var created = eventEntityRepository.save(eventEntity);

        var userForSendEmail = userRepository.findBySpheresInAndEventTypesIn(List.of(created.getSphere()),
            List.of(created.getEventType()));

        var jsonIds = userForSendEmail.stream().map(ITMOUser::getEmail).toList();

        try {
            rabbitTemplate.convertAndSend(RabbitConfig.QUEUE, objectMapper.writeValueAsString(jsonIds));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return BaseOperationResult
            .builder()
            .withStatus(OperationStatus.OK)
            .withEntityId(created.getId())
            .withMessage("Success event creation!")
            .build();
    }
}
