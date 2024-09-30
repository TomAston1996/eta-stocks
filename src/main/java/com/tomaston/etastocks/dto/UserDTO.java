package com.tomaston.etastocks.dto;

public record UserDTO(
        Integer userId,
        String email,
        Long createdOn
) {
}
