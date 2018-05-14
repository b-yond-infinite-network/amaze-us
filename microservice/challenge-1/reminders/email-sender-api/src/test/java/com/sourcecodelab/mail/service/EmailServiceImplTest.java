package com.sourcecodelab.mail.service;

import com.sourcecodelab.mail.rest.dto.MailDTO;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class EmailServiceImplTest {
    @Mock
    public JavaMailSender emailSender;
    @InjectMocks
    private EmailServiceImpl emailService;
    @Captor
    private ArgumentCaptor<SimpleMailMessage> simpleMailMessageArgumentCaptor;

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

        emailService.sendMail(mail);

        verify(emailSender).send(simpleMailMessageArgumentCaptor.capture());
        SimpleMailMessage value = simpleMailMessageArgumentCaptor.getValue();
        assertThat(mail.getRecipient()).isEqualTo(Lists.newArrayList(value.getTo()).get(0));
        assertThat(mail.getSender()).isEqualTo(value.getFrom());
        assertThat(mail.getSubject()).isEqualTo(value.getSubject());
        assertThat(mail.getText()).isEqualTo(value.getText());
    }
}