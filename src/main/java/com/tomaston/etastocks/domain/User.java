package com.tomaston.etastocks.domain;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record User(
        @Id
        Integer userId,
        @NotEmpty
        String email,
        LocalDateTime createdOn,
        String pass
) {}
