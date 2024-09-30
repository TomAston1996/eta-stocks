package com.tomaston.etastocks.domain;

public record Stock(
        Integer stockId,
        String symbol,
        String name,
        String type,
        String region,
        String currency
) {
}
