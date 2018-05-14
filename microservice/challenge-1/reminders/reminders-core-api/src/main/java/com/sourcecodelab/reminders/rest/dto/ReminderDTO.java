package com.sourcecodelab.reminders.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReminderDTO {
    private String id;
    @Email
    @NotNull
    @Size(min = 1, message = "Please, set an email address to send the text to it")
    private String recipient;
    private String text;
    private String cron;
    private String status;
}
