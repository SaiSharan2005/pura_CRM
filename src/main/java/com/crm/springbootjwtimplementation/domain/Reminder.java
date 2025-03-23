package com.crm.springbootjwtimplementation.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "reminders")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mandatory title
    @Column(nullable = false)
    private String title;

    // Note (can be long text)
    @Column(columnDefinition = "TEXT")
    private String note;

    // Location (optional)
    private String location;

    // Optional associated deal
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deal_id")
    private DealDetails deal;

    // The user who created the reminder; required.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Active flag
    @Column(nullable = false)
    private boolean isActive = true;

    // The datetime at which the reminder is scheduled
    @Column(name = "reminder_date", nullable = false)
    private LocalDateTime reminderDate;

    // Audit fields
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
