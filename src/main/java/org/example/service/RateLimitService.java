package org.example.service;

import lombok.extern.slf4j.Slf4j;
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

    public boolean isLimitReached(String ip) {
        rateLimits.compute(ip, (key, value) -> value == null ? rateLimit - 1 : value - 1);

        return rateLimits.get(ip) < 0;
    }

    @Scheduled(fixedRateString = "${app.refillInterval}")
    public void reportCurrentTime() {
        log.info("Refresh rate limits");

        rateLimits.replaceAll((k, v) -> rateLimit);
    }

}
