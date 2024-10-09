package com.tomaston.etastocks.dto;

public record StockDTO(
        Integer stockId,
        String symbol,
        String name,
        String type,
        String region,
        String currency
) {}
