package com.sourcecodelab.mail.service;

import com.sourcecodelab.mail.rest.dto.MailDTO;
import com.sourcecodelab.mail.service.exceptions.MailServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.sourcecodelab.mail.service.exceptions.ErrorCode.AWS_SES_ERROR;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    public final JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendMail(MailDTO mail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mail.getSender());
            message.setTo(mail.getRecipient());
            message.setSubject(mail.getSubject());
            message.setText(mail.getText());

            emailSender.send(message);
        } catch (MailException e) {
            log.error("An error occurred while sending the mail.", e);
            throw new MailServiceException(AWS_SES_ERROR);
        }
    }
}
