package com.educational.events.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
@Getter
@Setter
public class AuthOperationResult extends BaseOperationResult {

    /**
     * Токен авторизации.
     */
    private String token;
}
