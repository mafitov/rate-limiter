package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.RemoteAddress;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class RateLimitController {

    @GetMapping("/")
    public ResponseEntity<RemoteAddress> getIp(HttpServletRequest request) {
        log.info("Get IP for: {}", request.getRemoteAddr());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(RemoteAddress.builder()
                        .ip(request.getRemoteAddr())
                        .build());
    }

}
