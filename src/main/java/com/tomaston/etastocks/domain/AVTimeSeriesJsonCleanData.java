package com.tomaston.etastocks.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AVTimeSeriesJsonCleanData implements Serializable {
    public Long unixTimestamp;
    public Double openStockPrice;
    public Double closeStockPrice;
    public Double lowStockPrice;
    public Double highStockPrice;

    private AVTimeSeriesJsonCleanData(Long unixTimestamp, Double openStockPrice, Double closeStockPrice, Double lowStockPrice, Double highStockPrice) {
        this.unixTimestamp = unixTimestamp;
        this.openStockPrice = openStockPrice;
        this.closeStockPrice = closeStockPrice;
        this.lowStockPrice = lowStockPrice;
        this.highStockPrice = highStockPrice;
    }

    public static class Builder {
        private Long unixTimestamp;
        private Double openStockPrice;
        private Double closeStockPrice;
        private Double lowStockPrice;
        private Double highStockPrice;

        public Builder unixTimestamp(Long unixTimestamp) {
            this.unixTimestamp = unixTimestamp;
            return this;
        }

        public Builder openStockPrice(Double openStockPrice) {
            this.openStockPrice = openStockPrice;
            return this;
        }

        public Builder closeStockPrice(Double closeStockPrice) {
            this.closeStockPrice = closeStockPrice;
            return this;
        }

        public Builder lowStockPrice(Double lowStockPrice) {
            this.lowStockPrice = lowStockPrice;
            return this;
        }

        public Builder highStockPrice(Double highStockPrice) {
            this.highStockPrice = highStockPrice;
            return this;
        }

        public AVTimeSeriesJsonCleanData build() {
            validate();
            return new AVTimeSeriesJsonCleanData(unixTimestamp, openStockPrice, closeStockPrice, lowStockPrice, highStockPrice);
        }

        private void validate() {
            if (unixTimestamp == null || openStockPrice == null || closeStockPrice == null || lowStockPrice == null || highStockPrice == null) {
                throw new IllegalArgumentException("Values cannot be null");
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
