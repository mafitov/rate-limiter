package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class RemoteAddress {

    @JsonProperty("ip")
    private String ip;

}
