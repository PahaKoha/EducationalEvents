package com.educational.events.transfer;

import lombok.Getter;
import lombok.Setter;

/**
 * ТО с данными нового пользователя.
 */
@Getter
@Setter
public class NewUserDataTo {
    private String username;
    private String password;
    private String confirmedPassword;
    private String email;
}
