package org.softuni.mobilelele.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "user_activation_codes")
public class UserActivationCode extends BaseEntity {

    private String activationCode;

    private Instant created;

    @ManyToOne
    private User user;

    public String getActivationCode() {
        return activationCode;
    }

    public UserActivationCode setActivationCode(String activationCode) {
        this.activationCode = activationCode;
        return this;
    }

    public Instant getCreated() {
        return created;
    }

    public UserActivationCode setCreated(Instant created) {
        this.created = created;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserActivationCode setUser(User user) {
        this.user = user;
        return this;
    }
}
