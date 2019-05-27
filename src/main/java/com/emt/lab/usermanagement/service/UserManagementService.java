package com.emt.lab.usermanagement.service;

import com.emt.lab.usermanagement.model.exceptions.EmailAlreadyExistsException;
import com.emt.lab.usermanagement.model.User;
import com.emt.lab.usermanagement.model.dto.UserDto;
import com.emt.lab.usermanagement.model.exceptions.InvalidEmailException;
import com.emt.lab.usermanagement.model.exceptions.InvalidVerificationCode;
import com.emt.lab.usermanagement.model.exceptions.VerificationCodeExpired;

public interface UserManagementService {

    User registerUser(UserDto user) throws EmailAlreadyExistsException;

    void verifyUser(String verificationCode) throws InvalidVerificationCode, VerificationCodeExpired;

    void changeEmail();

    void forgotPassword(String email) throws InvalidEmailException;
}
