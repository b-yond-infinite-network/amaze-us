package com.sourcecodelab.reminders.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReminderListDTO {
    List<ReminderDTO> reminders;
}
