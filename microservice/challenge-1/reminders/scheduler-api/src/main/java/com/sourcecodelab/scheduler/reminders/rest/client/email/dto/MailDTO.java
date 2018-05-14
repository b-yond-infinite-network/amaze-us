package com.sourcecodelab.scheduler.reminders.rest.client.email.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailDTO {
    private String sender;
    private String recipient;
    private String subject;
    private String text;
    private String status;
}
