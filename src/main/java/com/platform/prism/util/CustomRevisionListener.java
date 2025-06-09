package com.platform.prism.util;

import com.platform.prism.model.CustomRevisionEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class CustomRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        CustomRevisionEntity rev = (CustomRevisionEntity) revisionEntity;

        // Username
        String username = "SYSTEM";
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                username = auth.getName();
            }
        } catch (Exception ignored) {
            log.error("Error auditing: {}", ignored.getMessage());
        }

        // IP Address
        String ipAddress = "UNKNOWN";
        try {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attr != null) {
                HttpServletRequest request = attr.getRequest();
                if (request != null) {
                    ipAddress = request.getRemoteAddr();
                }
            }
        } catch (Exception ignored) {
            log.error("Error auditing: {}", ignored.getMessage());
        }

        rev.setUsername(username);
        rev.setIpAddress(ipAddress);
    }
}
