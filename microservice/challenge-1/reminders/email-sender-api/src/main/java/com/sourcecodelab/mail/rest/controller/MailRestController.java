package com.sourcecodelab.mail.rest.controller;

import com.sourcecodelab.mail.rest.dto.MailDTO;
import com.sourcecodelab.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(methods = { RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class MailRestController implements MailRestAPI {

    private final EmailService emailService;

    @Autowired
    public MailRestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity sendMail(@RequestBody MailDTO mail) {
        emailService.sendMail(mail);
        mail.setStatus("SENT");
        return new ResponseEntity(mail, HttpStatus.OK);
    }
}
