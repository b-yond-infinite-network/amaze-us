package com.sourcecodelab.scheduler.reminders.rest.client.email;

import com.sourcecodelab.scheduler.reminders.rest.client.email.dto.MailDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class EmailClient {
    @Value("${email.service.url:http://localhost:8082/api/mails/v1}")
    private String emailServiceUrl;
    @Value("${email.service.send.email.path:/mail}")
    private String sendEmailServicePath;

    public MailDTO sendEmail(MailDTO mail) {
        return new RestTemplate().postForEntity(UriComponentsBuilder.fromUriString(emailServiceUrl).path(sendEmailServicePath).build().toUriString(), mail, MailDTO.class).getBody();
    }
}
