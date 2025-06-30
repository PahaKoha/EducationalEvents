package com.educational.events.usecase.event;

import com.educational.events.mapper.EventMapper;
import com.educational.events.model.BaseOperationResult;
import com.educational.events.model.BusinessFetchQueryParams;
import com.educational.events.model.EventFilter;
import com.educational.events.repository.EventEntityRepository;
import com.educational.events.transfer.EventProjection;
import com.educational.events.transfer.EventTo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventFetchUseCase {

    private final EventEntityRepository eventEntityRepository;
    private final ObjectMapper objectMapper;
    private final EventMapper eventMapper;

    public List<EventProjection> exec(BusinessFetchQueryParams<EventFilter> params) {
        try {
            var jsonParams = objectMapper.writeValueAsString(params);

            return eventEntityRepository.searchEntities(jsonParams);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public EventTo searchById (UUID id) {
        return eventMapper.convert(eventEntityRepository.findById(id).orElseThrow(RuntimeException::new));
    }
}
