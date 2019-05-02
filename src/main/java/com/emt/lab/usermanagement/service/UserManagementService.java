package com.emt.lab.usermanagement.service;

import com.emt.lab.usermanagement.model.User;

public interface UserManagementService {

    void registerUser(String email, String fullname, String passwordPlain);

    void verifyUser(User user);

    void changeEmail();
}
