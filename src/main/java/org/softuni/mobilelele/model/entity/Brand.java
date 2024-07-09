package org.softuni.mobilelele.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime modified;

    @OneToMany(mappedBy = "brand",
            cascade = CascadeType.ALL)
    private List<Model> models;

    public LocalDateTime getCreated() {
        return created;
    }

    public Brand setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public Brand setModified(LocalDateTime modified) {
        this.modified = modified;
        return this;
    }

    public String getName() {
        return name;
    }

    public Brand setName(String name) {
        this.name = name;
        return this;
    }

    public List<Model> getModels() {
        return models;
    }

    public Brand setModels(List<Model> models) {
        this.models = models;
        return this;
    }
}
