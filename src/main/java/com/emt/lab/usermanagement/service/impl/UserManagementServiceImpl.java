package com.emt.lab.usermanagement.service.impl;

import com.emt.lab.usermanagement.model.User;
import com.emt.lab.usermanagement.model.dto.UserDto;
import com.emt.lab.usermanagement.model.exceptions.EmailAlreadyExistsException;
import com.emt.lab.usermanagement.model.exceptions.InvalidEmailException;
import com.emt.lab.usermanagement.model.exceptions.InvalidVerificationCode;
import com.emt.lab.usermanagement.model.exceptions.VerificationCodeExpired;
import com.emt.lab.usermanagement.repository.UserRepository;
import com.emt.lab.usermanagement.repository.email.EmailSenderRepository;
import com.emt.lab.usermanagement.service.UserManagementService;
import org.springframework.stereotype.Service;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    private final EmailSenderRepository emailSenderRepository;

    public UserManagementServiceImpl(UserRepository userRepository, EmailSenderRepository emailSenderRepository) {
        this.userRepository = userRepository;
        this.emailSenderRepository = emailSenderRepository;
    }

    @Override
    public User registerUser(UserDto userDto) throws EmailAlreadyExistsException {
        if (userRepository.findByEmail(userDto.email) != null)
            throw new EmailAlreadyExistsException();

        User user = User.register(userDto);

        return this.userRepository.save(user);
    }

    @Override
    public void verifyUser(String verificationCode) throws InvalidVerificationCode, VerificationCodeExpired {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null)
            throw new InvalidVerificationCode();

        user.verify(verificationCode);
        userRepository.save(user);
    }

    @Override
    public void changeEmail() {

    }

    @Override
    public void forgotPassword(String email) throws InvalidEmailException {
        String[] parts = email.split("\"");
        email = parts[3];
        User user = userRepository.findByEmail(email);

        if (user == null)
            throw new InvalidEmailException();

        String newPassword = user.resetPassword();
        userRepository.save(user);

        emailSenderRepository.createAndSendEmail("no-reply@emt.com",
                user.email,
                "New system-generated password",
                "Your new password is: " + newPassword + "\n Please reset your password.");
    }
}
