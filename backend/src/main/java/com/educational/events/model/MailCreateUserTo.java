package com.educational.events.model;

import com.educational.events.model.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailCreateUserTo {

    private MessageType type;
    private String email;
    private String username;
}
