package com.sourcecodelab.mail.rest.controller;

import com.sourcecodelab.mail.rest.dto.MailDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.sourcecodelab.mail.rest.constants.APIConstants.PATH;
import static com.sourcecodelab.mail.rest.constants.APIConstants.SEND_PATH;

@RequestMapping(PATH)
public interface MailRestAPI {
    @PostMapping(path = SEND_PATH)
    ResponseEntity sendMail(@RequestBody MailDTO mail);
}
