package com.task.taskapi.domain.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Table(name = "tasks")
@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    private String title;
    private String description;
    private String status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User user;
}
