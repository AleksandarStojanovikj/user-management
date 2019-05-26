package com.emt.lab.usermanagement.repository.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderRepositoryImpl implements EmailSenderRepository {

    private final JavaMailSender mailSender;

    public EmailSenderRepositoryImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(SimpleMailMessage message) {

    }

    @Override
    public void createAndSendEmail(String from, String to, String subject, String content) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(from);
        email.setTo(to);
        email.setSubject(subject);
        email.setText(content);
        mailSender.send(email);
    }
}
