package com.emt.lab.usermanagement.service.impl;

import com.emt.lab.usermanagement.model.User;
import com.emt.lab.usermanagement.model.UserRole;
import com.emt.lab.usermanagement.model.dto.UserDto;
import com.emt.lab.usermanagement.repository.UserRepository;
import com.emt.lab.usermanagement.repository.UserRoleRepository;
import com.emt.lab.usermanagement.service.CreateAdminService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CreateAdminServiceImpl implements CreateAdminService {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    @Value("${app.admin.fullName}")
    private String fullName;

    @Value("${app.admin.email}")
    private String email;

    @Value("${app.admin.password}")
    private String password;

    @Value("${app.admin.address}")
    private String address;

    @Value("${app.admin.city}")
    private String city;

    public CreateAdminServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createAdmin() {
        UserRole adminRole = this.userRoleRepository.findByName("ADMIN").get();

        User admin = userRepository.findByEmail(email);
        if (admin == null) {
            UserDto userDto = new UserDto(email, fullName, password, password, address, city);
            admin = User.register(userDto, adminRole);
            admin.isVerified = true;
            this.userRepository.save(admin);
        }
    }
}
