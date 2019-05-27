package com.emt.lab.usermanagement.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @ManyToMany
    public Set<User> users;

    public UserRole() {

    }

    public UserRole(String name) {
        this.name = name;
    }
}
