package com.sourcecodelab.mail.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO {
    @Email
    @NotNull
    @Size(min = 1, message = "Please, set an email address to send the text from it")
    private String sender;
    @Email
    @NotNull
    @Size(min = 1, message = "Please, set an email address to send the text to it")
    private String recipient;
    private String subject;
    private String text;
    private String status;
}
