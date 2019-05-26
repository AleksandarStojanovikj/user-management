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

}
