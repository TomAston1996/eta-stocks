package com.tomaston.etastocks.dto;

import org.springframework.data.relational.core.sql.In;

public class TestDTO {
    private final Integer id;
    private final String streetName;
    private final Integer houseNumber;
    private final String city;

    public TestDTO(Integer id, String streetName, Integer houseNumber, String city) {
        this.id = id;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getStreetName() {
        return streetName;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public static class Builder {
        private Integer id;
        private String streetName;
        private Integer houseNumber;
        private String city;

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setStreetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        public Builder setHouseNumber(Integer houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public TestDTO build() {
            return new TestDTO(id, streetName, houseNumber, city);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
