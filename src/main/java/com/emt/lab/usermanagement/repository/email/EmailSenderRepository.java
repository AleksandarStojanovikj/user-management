package com.emt.lab.usermanagement.repository.email;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderRepository {
    void sendEmail (SimpleMailMessage message);

    void createAndSendEmail(String from, String to, String subject, String content);
}
