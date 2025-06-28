package com.educational.events.usecase.event;

import com.educational.events.mapper.EventMapper;
import com.educational.events.model.BaseOperationResult;
import com.educational.events.model.BusinessFetchQueryParams;
import com.educational.events.model.EventFilter;
import com.educational.events.repository.EventEntityRepository;
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

    public List<EventTo> exec(BusinessFetchQueryParams<EventFilter> params) {
        try {
            var jsonParams = objectMapper.writeValueAsString(params);
            var eventEntities = eventEntityRepository.searchEntities(jsonParams);

            return eventEntities
                .stream()
                .filter(Objects::nonNull)
                .map(eventMapper::convert)
                .toList();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public EventTo searchById (UUID id) {
        return eventMapper.convert(eventEntityRepository.findById(id).orElseThrow(RuntimeException::new));
    }
}
