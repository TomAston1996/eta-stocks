package com.tomaston.etastocks.controller;

import com.tomaston.etastocks.dto.TestDTO;
import com.tomaston.etastocks.service.TestService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/{testId}")
    public ResponseEntity<TestDTO> getTest(@PathVariable Integer testId) {
        return ResponseEntity.ok(testService.getTestData(testId));
    }
}
