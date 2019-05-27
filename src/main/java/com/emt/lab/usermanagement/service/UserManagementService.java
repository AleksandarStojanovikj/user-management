package com.emt.lab.usermanagement.service;

import com.emt.lab.usermanagement.model.dto.UserDetailsDto;
import com.emt.lab.usermanagement.model.exceptions.*;
import com.emt.lab.usermanagement.model.User;
import com.emt.lab.usermanagement.model.dto.UserDto;

public interface UserManagementService {

    User registerUser(UserDto user) throws EmailAlreadyExistsException;

    void verifyUser(String verificationCode) throws InvalidVerificationCode, VerificationCodeExpired;

    void changeEmail();

    void forgotPassword(String email) throws InvalidEmailException;

    void editDetails(UserDetailsDto userDetailsDto) throws UserDoesNotExistException;
}
