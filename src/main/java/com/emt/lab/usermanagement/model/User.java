package com.emt.lab.usermanagement.model;

import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    @Id
    @NotNull
    public Long id;

    @Column(unique = true)
    public String email;

    public String password;

    public String fullname;

    public boolean isVerified;

    public User register(String email, String password, String fullname) {
        return this;
    }

    public User verify(String code) {
        return this;
    }

    public User changeEmail(String email) {
        return this;
    }

    public User changePassword(String password) {
        return this;
    }

}
