package com.tomaston.etastocks.dto;

public record AVTickerDataDTO(
        String symbol,
        String name,
        String type,
        String region,
        String currency
) {
}
