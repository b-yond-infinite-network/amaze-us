package com.sourcecodelab.mail.rest.controller;

import com.sourcecodelab.mail.rest.dto.MailDTO;
import com.sourcecodelab.mail.service.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

public class MailRestControllerTest {
    @Mock
    private EmailService emailService;
    @InjectMocks
    private MailRestController mailRestController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendMail() {
        MailDTO mail = MailDTO.builder()
                .recipient("test@email.com")
                .sender("no-reply@sourcecodelab.com")
                .subject("Reminder")
                .text("Wake up")
                .build();

        MailDTO value = (MailDTO)mailRestController.sendMail(mail).getBody();

        assertThat(value.getStatus()).isEqualTo("SENT");
    }
}