package com.quran.api.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quran.api.entity.RequestDetails;
import com.quran.api.repo.RequestDetailsRepository;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RequestLoggingAspect {

    @Autowired
    private RequestDetailsRepository requestRepo;
    
    @Autowired
	private HttpServletRequest request;

    @AfterReturning("execution(* com.quran.api.controller..*(..))")
    public void logRequestDetails() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		RequestDetails details = new RequestDetails(request.getRequestURI(), getClientIp(), sdf.format(new Date()));
		requestRepo.save(details);
    }
    
    public String getClientIp() {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}

