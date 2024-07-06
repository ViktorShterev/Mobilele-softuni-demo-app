package org.softuni.mobilelele.model.events;

import org.springframework.context.ApplicationEvent;

public class UserRegisteredEvent extends ApplicationEvent {

    private final String userEmail;
    private final String username;

    public UserRegisteredEvent(Object source, String userEmail, String userName) {
        super(source);
        this.userEmail = userEmail;
        this.username = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUsername() {
        return username;
    }
}
