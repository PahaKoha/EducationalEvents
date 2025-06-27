package com.educational.events.controller;

import com.educational.events.model.BaseOperationResult;
import com.educational.events.transfer.EventTo;
import com.educational.events.usecase.event.EventCreateUseCase;
import com.educational.events.usecase.event.EventUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/create")
    public BaseOperationResult createEvent(@RequestBody EventTo event) {
        return createUseCase.exec(event);
    }

    @PutMapping("/update")
    public BaseOperationResult updateEvent(@RequestBody EventTo event) {
        return updateUseCase.exec(event);
    }
}
