package com.crm.springbootjwtimplementation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "targets")
public class Target {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDate deadline;
    private Boolean isAchieved = false;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id", nullable = false)
    private User assignedToId; // Generalized assignee (Salesman, LogisticPerson, etc.)

    @ManyToOne
    @JoinColumn(name = "assigned_by_id", nullable = false)
    private User assignedById; // Manager or whoever assigns the target

    private String assigneeRole; // e.g., "Salesman", "LogisticPerson", etc.

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
