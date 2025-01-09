package com.tomaston.etastocks.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.junit.jupiter.api.Assertions.*;

class HealthControllerIntegrationTest {

    String TEST_URL = "http://localhost:8080";

    public void setup() {
    }

    @Test
    @DisplayName("Should return OK (200) on liveness check")
    public void shouldReturnLivenessOK() throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(TEST_URL + "/liveness"))
            .build();

        HttpResponse<String> response = client.send(request, ofString());

        assertEquals(response.statusCode(), 200);
        assertEquals(response.body(), "OK");
    }

    @Test
    @DisplayName("Should return OK (200) on readiness check")
    public void shouldReturnReady() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TEST_URL + "/readiness"))
                .build();

            HttpResponse<String> response = client.send(request, ofString());

            assertEquals(response.statusCode(), 200);
            assertEquals(response.body(), "OK");
        } catch (Exception e) {
            fail("Should return OK (200) on readiness check");
        }
    }

}