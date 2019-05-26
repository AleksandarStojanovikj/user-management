package com.emt.lab.usermanagement.service.impl;

import com.emt.lab.usermanagement.model.User;
import com.emt.lab.usermanagement.model.dto.LoginInfo;
import com.emt.lab.usermanagement.model.exceptions.InvalidCredentialsException;
import com.emt.lab.usermanagement.repository.UserRepository;
import com.emt.lab.usermanagement.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long loginUser(LoginInfo loginInfo) throws InvalidCredentialsException {
        User user = userRepository.findByEmail(loginInfo.email);

        if (user == null)
            throw new InvalidCredentialsException();

        if (!user.canLogin(loginInfo.password))
            throw new InvalidCredentialsException();

        return user.id;
    }
}
