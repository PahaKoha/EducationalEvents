package com.educational.events.model;

import com.educational.events.model.enums.MessageType;
import com.educational.events.transfer.MailEventLiteInformation;
import com.educational.events.transfer.MailUserLiteInformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailCreateEventTo {

    private MessageType messageType;
    private MailEventLiteInformation event;
    private List<MailUserLiteInformation> receivers;


}