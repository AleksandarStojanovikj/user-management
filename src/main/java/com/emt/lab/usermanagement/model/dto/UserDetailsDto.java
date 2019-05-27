package com.emt.lab.usermanagement.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDetailsDto {
    @NotNull
    @NotEmpty
    public Long id;

    @NotNull
    @NotEmpty
    public String email;

    @NotNull
    @NotEmpty
    public String fullName;

    @NotNull
    @NotEmpty
    public String city;

    @NotNull
    @NotEmpty
    public String address;
}
