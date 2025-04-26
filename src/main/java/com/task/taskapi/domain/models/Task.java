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

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User user;

    //Si el estado es null asigna uno por defecto
    @PrePersist
    private void setDefaultStatus(){
        if(this.status == null){
            this.status = TaskStatus.PENDING;
        }
    }
}
