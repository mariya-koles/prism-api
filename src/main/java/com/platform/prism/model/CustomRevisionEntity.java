package com.platform.prism.model;

import com.platform.prism.util.CustomRevisionListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@Setter
@Getter
@RevisionEntity(CustomRevisionListener.class)
public class CustomRevisionEntity extends DefaultRevisionEntity {
    @Column(name = "username")
    private String username;

    @Column(name = "ip_address")
    private String ipAddress;
}
