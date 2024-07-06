package org.softuni.mobilelele.model.entity;

import jakarta.persistence.*;
import org.softuni.mobilelele.model.enums.RolesEnum;

@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private RolesEnum name;

    public RolesEnum getRole() {
        return name;
    }

    public UserRole setRole(RolesEnum name) {
        this.name = name;
        return this;
    }
}
