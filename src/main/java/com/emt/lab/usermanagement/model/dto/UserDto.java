package com.emt.lab.usermanagement.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDto {
    @NotNull
    @NotEmpty
    public String email;

    @NotNull
    @NotEmpty
    public String fullname;

    @NotNull
    @NotEmpty
    public String password;

    @NotNull
    @NotEmpty
    public String confirmPassword;

    @NotNull
    @NotEmpty
    public String address;

    @NotNull
    @NotEmpty
    public String city;

    public UserDto(@NotNull @NotEmpty String email, @NotNull @NotEmpty String fullname, @NotNull @NotEmpty String password, @NotNull @NotEmpty String confirmPassword, @NotNull @NotEmpty String address, @NotNull @NotEmpty String city) {
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.address = address;
        this.city = city;
    }
}
