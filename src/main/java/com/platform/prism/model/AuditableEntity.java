package com.platform.prism.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.boot.actuate.audit.listener.AuditListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class AuditableEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
