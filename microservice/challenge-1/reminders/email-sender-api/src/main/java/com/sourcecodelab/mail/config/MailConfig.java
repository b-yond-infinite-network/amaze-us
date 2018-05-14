package com.sourcecodelab.mail.config;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {
    @Autowired
    AmazonSimpleEmailService amazonSimpleEmailService(AmazonSimpleEmailService amazonSimpleEmailService) {
        return amazonSimpleEmailService;
    }
}
