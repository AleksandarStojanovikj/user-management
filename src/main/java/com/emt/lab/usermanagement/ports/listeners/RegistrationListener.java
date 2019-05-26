package com.emt.lab.usermanagement.ports.listeners;

import com.emt.lab.usermanagement.events.OnRegistrationEvent;
import com.emt.lab.usermanagement.model.User;
import com.emt.lab.usermanagement.repository.email.EmailSenderRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener {
    private final EmailSenderRepository emailSenderRepository;


    public RegistrationListener(EmailSenderRepository emailSenderRepository) {
        this.emailSenderRepository = emailSenderRepository;
    }

    @EventListener
    public void onRegister(OnRegistrationEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationEvent event) {
        User user = event.getUser();

        String mailTo = user.email;
        String subject = "Verify Email";
        String confirmationUrl = "http://localhost:8080/verify?verificationCode=" + user.verificationCode;
        emailSenderRepository.createAndSendEmail("no-reply@emt.com", mailTo, subject, confirmationUrl);
    }
}
