package com.emt.lab.usermanagement.service.impl;

import com.emt.lab.usermanagement.model.User;
import com.emt.lab.usermanagement.model.UserRole;
import com.emt.lab.usermanagement.model.dto.UserDetailsDto;
import com.emt.lab.usermanagement.model.dto.UserDto;
import com.emt.lab.usermanagement.model.exceptions.*;
import com.emt.lab.usermanagement.repository.UserRoleRepository;
import com.emt.lab.usermanagement.repository.UserRepository;
import com.emt.lab.usermanagement.repository.email.EmailSenderRepository;
import com.emt.lab.usermanagement.service.UserManagementService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    private final EmailSenderRepository emailSenderRepository;
    private final UserRoleRepository roleRepository;

    public UserManagementServiceImpl(UserRepository userRepository,
                                     EmailSenderRepository emailSenderRepository,
                                     UserRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.emailSenderRepository = emailSenderRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User registerUser(UserDto userDto) throws EmailAlreadyExistsException {
        if (userRepository.findByEmail(userDto.email) != null)
            throw new EmailAlreadyExistsException();

        Optional<UserRole> userRole = this.roleRepository.findByName("USER");
        User user = User.register(userDto, userRole.get());

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
                "Your new password is: " + newPassword + "\nPlease reset your password.");
    }

    @Override
    public void editDetails(UserDetailsDto userDetailsDto) throws UserDoesNotExistException {
        User user = userRepository.findById(userDetailsDto.id)
                .orElseThrow(UserDoesNotExistException::new);

        user.editDetails(userDetailsDto);
        userRepository.save(user);
    }
}
