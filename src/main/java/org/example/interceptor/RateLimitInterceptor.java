package org.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.RateLimitService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
@AllArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    RateLimitService rateLimitService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (rateLimitService.isLimitReached(request.getRemoteAddr())) {
            log.info("Limit reached for IP: {}", request.getRemoteAddr());
            response.setStatus(429);
            return false;
        }
        return true;
    }

}
