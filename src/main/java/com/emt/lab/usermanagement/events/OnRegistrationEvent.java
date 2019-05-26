package com.emt.lab.usermanagement.events;

import com.emt.lab.usermanagement.model.User;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationEvent extends ApplicationEvent {
    private User user;

    public OnRegistrationEvent(User user) {
        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
