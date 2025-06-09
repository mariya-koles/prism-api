package com.platform.prism.util;

import com.platform.prism.service.AuditService;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditListener {

    private static AuditService auditService;

    @Autowired
    public void init(AuditService auditService) {
        AuditListener.auditService = auditService;
    }

    @PostPersist
    public void afterCreate(Object entity) {
        auditService.log("CREATE", entity.getClass().getSimpleName(), extractId(entity), null, null, null, null, null, null, null);
    }

    @PostUpdate
    public void afterUpdate(Object entity) {
        auditService.log("UPDATE", entity.getClass().getSimpleName(), extractId(entity), null, null, null, null, null, null, null);
    }

    @PostRemove
    public void afterDelete(Object entity) {
        auditService.log("DELETE", entity.getClass().getSimpleName(), extractId(entity), null, null, null, null, null, null, null);
    }

    @PostLoad
    public void afterLoad(Object entity) {
        auditService.log("GET", entity.getClass().getSimpleName(), extractId(entity), null, null, null, null, null, null, null);
    }

    private Long extractId(Object entity) {
        try {
            return (Long) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            return null;
        }
    }
}
