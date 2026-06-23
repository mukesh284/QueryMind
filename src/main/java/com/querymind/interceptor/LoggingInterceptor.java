package com.querymind.interceptor;

import com.querymind.service.logging.AuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * HTTP interceptor for logging API requests
 */
@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {
    private final AuditService auditService;

    public LoggingInterceptor(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String endpoint = request.getRequestURI();
        String method = request.getMethod();
        String ipAddress = getClientIP(request);

        auditService.logAPIRequest(endpoint, method, ipAddress);
        return true;
    }

    /**
     * Get client IP address
     */
    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

