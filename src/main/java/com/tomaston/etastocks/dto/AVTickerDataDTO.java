package com.tomaston.etastocks.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AVTickerDataDTO implements Serializable {
    public String symbol;
    public String name;
    public String type;
    public String region;
    public String currency;

    private AVTickerDataDTO(String symbol, String name, String type, String region, String currency) {
        this.symbol = symbol;
        this.name = name;
        this.type = type;
        this.region = region;
        this.currency = currency;
    }

    public static class Builder {
        private String symbol;
        private String name;
        private String type;
        private String region;
        private String currency;

        public Builder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public AVTickerDataDTO build() {
            validate();
            return new AVTickerDataDTO(symbol, name, type, region, currency);
        }

        private void validate() {
            if (symbol == null) {
                throw new IllegalArgumentException("Values cannot be null");
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
