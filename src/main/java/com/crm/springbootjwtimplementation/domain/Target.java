package com.crm.springbootjwtimplementation.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "targets")
public class Target {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Target description
    @Column(nullable = false)
    private String description;

    // Deadline for the target
    @Column(nullable = false)
    private LocalDate deadline;

    // Whether the target has been achieved
    @Column(name = "is_achieved", nullable = false)
    private boolean isAchieved = false;

    // Monthly outlet count target
    @Column(name = "monthly_outlet_count", nullable = false)
    private int monthlyOutletCount;

    // Monthly revenue or sales amount target
    @Column(name = "monthly_target_amount", nullable = false)
    private BigDecimal monthlyTargetAmount;

    // The manager who created the target.
    // Taken from the authenticated user.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private ManagerDetails manager;

    // The salesman for whom this target is set.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salesman_id", nullable = false)
    private SalesmanDetails salesman;

    // The month-year identifier (e.g., "2025-03")
    @Column(name = "month_year", nullable = false)
    private String monthYear;

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
