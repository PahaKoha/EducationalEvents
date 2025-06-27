package com.educational.events.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateUserData {

    private UUID id;

    private String updatedPassword;

    private String confirmedUpdatedPassword;

    private List<Long> interestSphereIds;

    private List<Long> interestEventTypeIds;
}
