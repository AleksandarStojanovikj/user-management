package com.emt.lab.usermanagement.model;

import com.emt.lab.usermanagement.model.dto.UserDetailsDto;
import com.emt.lab.usermanagement.model.dto.UserDto;
import com.emt.lab.usermanagement.model.exceptions.InvalidVerificationCode;
import com.emt.lab.usermanagement.model.exceptions.VerificationCodeExpired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public LocalDateTime createdOn;

    @Column(unique = true)
    public String email;

    public String password;

    public String fullname;

    public String city;

    public String address;

    public String verificationCode;

    public boolean isVerified;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    public Set<UserRole> role = new HashSet<>();

    public User() {
    }

    public static User register(UserDto userDto, UserRole role) {
        User user = new User();
        user.email = userDto.email;
        user.fullname = userDto.fullname;
        user.verificationCode = UUID.randomUUID().toString();
        user.createdOn = LocalDateTime.now();
        user.address = userDto.address;
        user.city = userDto.city;
        user.password = encodePassword(userDto.password);
        user.role.add(role);
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
        this.password = encodePassword(newPassword);
        return newPassword;
    }

    public static String encodePassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(plainPassword);

        return encodedPassword;
    }

    private boolean matchesPassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(plainPassword, this.password);
    }

    public boolean canLogin(String password) {
        return isVerified && matchesPassword(password);
    }

    public User changeEmail(String email) {
        return this;
    }

    public User changePassword(String password) {
        return this;
    }

    public void editDetails(UserDetailsDto userDetailsDto) {
        this.city = userDetailsDto.city;
        this.address = userDetailsDto.address;
        this.fullname = userDetailsDto.fullName;
        this.email = userDetailsDto.email;
    }
}
