package com.educational.events.controller;

import com.educational.events.model.BaseOperationResult;
import com.educational.events.model.BusinessFetchQueryParams;
import com.educational.events.model.EventFilter;
import com.educational.events.transfer.EventProjection;
import com.educational.events.transfer.EventTo;
import com.educational.events.usecase.event.EventCreateUseCase;
import com.educational.events.usecase.event.EventFetchUseCase;
import com.educational.events.usecase.event.EventUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для сущности "Событие"
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/event")
@EnableAspectJAutoProxy
public class EventController {

    private final EventCreateUseCase createUseCase;
    private final EventUpdateUseCase updateUseCase;
    private final EventFetchUseCase eventFetchUseCase;

    @PostMapping("/create")
    public BaseOperationResult createEvent(@RequestBody EventTo event) {
        return createUseCase.exec(event);
    }

    @PutMapping("/update")
    public BaseOperationResult updateEvent(@RequestBody EventTo event) {
        return updateUseCase.exec(event);
    }

    @PostMapping("/search")
    public List<EventProjection> search(@RequestBody BusinessFetchQueryParams<EventFilter> params) {
        return eventFetchUseCase.exec(params);
    }

    @GetMapping("/{id}")
    public EventTo searchById(@PathVariable UUID id) {
        return eventFetchUseCase.searchById(id);
    }


}