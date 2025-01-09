package com.tomaston.etastocks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private static final Logger log = LoggerFactory.getLogger(AlphaVantageController.class);

    /**
     * Liveness probe
     * @return ok
     */
    @GetMapping(value="/liveness", produces="text/plain")
    @CrossOrigin(origins = "http://localhost:3000")
    public String getLiveness() {
        log.info("Server is live...");
        return "OK";
    }

    /**
     * Readiness probe
     * @return ok
     */
    @GetMapping(value="/readiness", produces="text/plain")
    @CrossOrigin(origins = "http://localhost:3000")
    public String getReadiness() {
        log.info("Server is ready...");
        return "OK";
    }
}
