package com.emt.lab.usermanagement.ports;

import com.emt.lab.usermanagement.events.OnRegistrationEvent;
import com.emt.lab.usermanagement.model.User;
import com.emt.lab.usermanagement.model.dto.LoginInfo;
import com.emt.lab.usermanagement.model.dto.UserDto;
import com.emt.lab.usermanagement.model.exceptions.EmailAlreadyExistsException;
import com.emt.lab.usermanagement.model.exceptions.InvalidVerificationCode;
import com.emt.lab.usermanagement.model.exceptions.VerificationCodeExpired;
import com.emt.lab.usermanagement.repository.UserRepository;
import com.emt.lab.usermanagement.service.AuthenticationService;
import com.emt.lab.usermanagement.service.UserManagementService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {
    private final UserManagementService userManagementService;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AuthenticationService authenticationService;

    public UsersController(ApplicationEventPublisher applicationEventPublisher, UserManagementService userManagementService, UserRepository userRepository, AuthenticationService authenticationService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.userManagementService = userManagementService;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/test")
    public ResponseEntity testMe() {
        List<User> users = userRepository.findAll();
        if (users == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no");
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody LoginInfo loginInfo) {
        try {
            Long userId = authenticationService.loginUser(loginInfo);
            return ResponseEntity.status(HttpStatus.OK).body(userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDto user, HttpServletResponse response) throws EmailAlreadyExistsException {
        User newUser = this.userManagementService.registerUser(user);

        this.applicationEventPublisher.publishEvent(new OnRegistrationEvent(newUser));

        response.setHeader("location", "/app/accounts/" + newUser.id);
    }

    @GetMapping("/verify")
    public ResponseEntity verifyUser(@RequestParam String verificationCode) {
        try {
            userManagementService.verifyUser(verificationCode);
            return ResponseEntity.status(HttpStatus.OK).body("User verified");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
