package com.emt.lab.usermanagement.model;

import com.emt.lab.usermanagement.model.dto.UserDto;
import com.emt.lab.usermanagement.model.exceptions.InvalidVerificationCode;
import com.emt.lab.usermanagement.model.exceptions.VerificationCodeExpired;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public LocalDateTime createdOn;

    @Column(unique = true)
    public String email;

    public String password;

    public String fullname;

    public String address;

    public String verificationCode;

    public boolean isVerified;

    public User() {
    }

    public static User register(UserDto userDto) {
        User user = new User();
        user.email = userDto.email;
        user.fullname = userDto.fullname;
        user.verificationCode = UUID.randomUUID().toString();
        user.createdOn = LocalDateTime.now();
        user.address = userDto.address;
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        user.password = passwordEncoder.encode(userDto.password);
        user.password = userDto.password;
        return user;
    }

    public void verify(String verificationCode) throws VerificationCodeExpired, InvalidVerificationCode {
        if (isExpired())
            throw new VerificationCodeExpired();

        if (!this.verificationCode.equals(verificationCode))
            throw new InvalidVerificationCode();

        this.isVerified = true;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(createdOn.plusHours(24L));
    }

    public String resetPassword() {
        String newPassword = UUID.randomUUID().toString();
//        this.password - > encode
        return newPassword;
    }

    public boolean canLogin(String password) {
        return isVerified && password.equals(this.password);
    }

    public User changeEmail(String email) {
        return this;
    }

    public User changePassword(String password) {
        return this;
    }

}
