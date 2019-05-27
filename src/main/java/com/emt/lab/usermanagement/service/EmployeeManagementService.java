package com.emt.lab.usermanagement.service;

import com.emt.lab.usermanagement.model.User;
import com.emt.lab.usermanagement.model.dto.UserDetailsDto;
import com.emt.lab.usermanagement.model.dto.UserDto;
import com.emt.lab.usermanagement.model.exceptions.EmailAlreadyExistsException;
import com.emt.lab.usermanagement.model.exceptions.UserDoesNotExistException;

import java.util.List;

public interface EmployeeManagementService {
    User createEmployee(UserDto userDto) throws EmailAlreadyExistsException;
    User editEmployee(UserDetailsDto userDetailsDto) throws UserDoesNotExistException;
    void deleteEmployee(Long userId) throws UserDoesNotExistException;
    User getEmployeeById(Long userId) throws UserDoesNotExistException;
    List<User> getAllEmployees();
}
