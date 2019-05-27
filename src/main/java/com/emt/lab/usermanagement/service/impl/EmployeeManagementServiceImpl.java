package com.emt.lab.usermanagement.service.impl;

import com.emt.lab.usermanagement.model.User;
import com.emt.lab.usermanagement.model.UserRole;
import com.emt.lab.usermanagement.model.dto.UserDetailsDto;
import com.emt.lab.usermanagement.model.dto.UserDto;
import com.emt.lab.usermanagement.model.exceptions.EmailAlreadyExistsException;
import com.emt.lab.usermanagement.model.exceptions.UserDoesNotExistException;
import com.emt.lab.usermanagement.repository.UserRepository;
import com.emt.lab.usermanagement.repository.UserRoleRepository;
import com.emt.lab.usermanagement.service.EmployeeManagementService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeManagementServiceImpl implements EmployeeManagementService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public EmployeeManagementServiceImpl(UserRepository userRepository,
                                         UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public User createEmployee(UserDto userDto) throws EmailAlreadyExistsException {
        if (userRepository.findByEmail(userDto.email) != null)
            throw new EmailAlreadyExistsException();

        Optional<UserRole> userRole = this.userRoleRepository.findByName("EMPLOYEE");
        User user = User.register(userDto, userRole.get());

        return this.userRepository.save(user);
    }

    @Override
    public User editEmployee(UserDetailsDto userDetailsDto) throws UserDoesNotExistException {
        User user = userRepository.findById(userDetailsDto.id)
                .orElseThrow(UserDoesNotExistException::new);

        user.editDetails(userDetailsDto);
        user = userRepository.save(user);

        return user;
    }

    @Override
    public void deleteEmployee(Long userId) throws UserDoesNotExistException {
        User user = userRepository.findById(userId)
                .orElseThrow(UserDoesNotExistException::new);

        userRepository.delete(user);
    }

    @Override
    public User getEmployeeById(Long userId) throws UserDoesNotExistException {
        return userRepository.findById(userId).orElseThrow(UserDoesNotExistException::new);
    }

    @Override
    public List<User> getAllEmployees() {
        return userRepository.findAll();
    }
}
