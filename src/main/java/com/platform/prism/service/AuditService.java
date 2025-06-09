package com.platform.prism.service;

import com.platform.prism.model.AuditLog;
import com.platform.prism.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void log(String action,
                    String entityName,
                    Long entityId,
                    String changedField,
                    String oldValue,
                    String newValue,
                    String username,
                    String ipAddress,
                    UUID requestId,
                    String contextJson) {

        AuditLog log = AuditLog.builder()
                .action(action)
                .entityName(entityName)
                .entityId(entityId)
                .changedField(changedField)
                .oldValue(oldValue)
                .newValue(newValue)
                .username(username != null ? username : "system")
                .ipAddress(ipAddress)
                .requestId(requestId)
                .context(contextJson)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }
}
