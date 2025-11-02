package org.example.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RateLimit {

    private boolean isLimitReached;

    private String rateLimit;

    private String remaining;

    private String reset;

}
