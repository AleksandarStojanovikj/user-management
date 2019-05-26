package com.emt.lab.usermanagement.service;

import com.emt.lab.usermanagement.model.dto.LoginInfo;
import com.emt.lab.usermanagement.model.exceptions.InvalidCredentialsException;

public interface AuthenticationService {
    Long loginUser(LoginInfo loginInfo) throws InvalidCredentialsException;
}
