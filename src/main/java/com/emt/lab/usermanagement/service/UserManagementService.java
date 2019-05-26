package com.emt.lab.usermanagement.service;

import com.emt.lab.usermanagement.model.exceptions.EmailAlreadyExistsException;
import com.emt.lab.usermanagement.model.User;
import com.emt.lab.usermanagement.model.dto.UserDto;

public interface UserManagementService {

    User registerUser(UserDto user) throws EmailAlreadyExistsException;

    void verifyUser(User user);

    void changeEmail();
}
