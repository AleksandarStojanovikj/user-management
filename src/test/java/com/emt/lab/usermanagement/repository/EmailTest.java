package com.emt.lab.usermanagement.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmailTest {

    @Autowired
    JavaMailSender mailSender;

    @Test
    public void sendSimpleMail(){
        String userEmail = "test.test@email.com";
        String subject = "Test test";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("maemae@mae.com");
        email.setTo(userEmail);
        email.setSubject(subject);
        email.setText("mae mae ace");
        mailSender.send(email);
    }
}
