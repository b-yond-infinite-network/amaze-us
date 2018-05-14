package com.sourcecodelab.mail.service;

import com.sourcecodelab.mail.rest.dto.MailDTO;

public interface EmailService {
    void sendMail(MailDTO mail);
}
