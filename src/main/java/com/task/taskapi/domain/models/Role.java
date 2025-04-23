package com.task.taskapi.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "roles")
@Entity
@Data
public class Role {

    @Id
    @Column(name = "id")
    private String id;
    private String description;
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

}
