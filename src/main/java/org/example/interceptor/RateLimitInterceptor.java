package org.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.RateLimit;
import org.example.service.RateLimitService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
@AllArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    public static final String X_RATE_LIMIT_HEADER = "X-RateLimit-Limit";
    public static final String X_RATE_LIMIT_REMAINING_HEADER = "X-RateLimit-Remaining";
    public static final String X_RATE_LIMIT_RESET_HEADER = "X-RateLimit-Reset";

    RateLimitService rateLimitService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RateLimit rateLimit = rateLimitService.getRateLimit(request.getRemoteAddr());
        response.setHeader(X_RATE_LIMIT_HEADER, rateLimit.getRateLimit());
        response.setHeader(X_RATE_LIMIT_REMAINING_HEADER, rateLimit.getRemaining());

        if (rateLimit.isLimitReached()) {
            log.info("Limit reached for IP: {}", request.getRemoteAddr());
            response.setStatus(429);
            response.setHeader(X_RATE_LIMIT_RESET_HEADER, rateLimit.getReset());
            return false;
        }

        return true;
    }

}
