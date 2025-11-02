package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.RateLimit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RateLimitService {

    private final Map<String, Integer> rateLimits = new HashMap<>();

    @Value("${app.rateLimit}")
    private int rateLimit;

    @Value("${app.resetInterval}")
    private int resetInterval;

    private long resetTime;

    public RateLimit getRateLimit(String ip) {
        return RateLimit.builder()
                .isLimitReached(isLimitReached(ip))
                .rateLimit(String.valueOf(rateLimit))
                .remaining(String.valueOf(Math.max(0, rateLimits.getOrDefault(ip, rateLimit))))
                .reset(String.valueOf(resetTime + resetInterval))
                .build();
    }

    private boolean isLimitReached(String ip) {
        rateLimits.compute(ip, (key, value) -> value == null ? rateLimit - 1 : value - 1);

        return rateLimits.get(ip) < 0;
    }

    @Scheduled(fixedRateString = "${app.resetInterval}")
    public void resetRateLimits() {
        log.info("Reset Rate limits");

        resetTime = System.currentTimeMillis();

        rateLimits.replaceAll((k, v) -> rateLimit);
    }

}
