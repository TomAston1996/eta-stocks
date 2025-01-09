package com.tomaston.etastocks.domain;

public class TestModel {
    private final Integer id;
    private final String streetName;
    private final Integer houseNumber;
    private final String city;
    private final String personalInfo;

    public TestModel(Integer id, String streetName, Integer houseNumber, String city, String personalInfo) {
        this.id = id;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.city = city;
        this.personalInfo = personalInfo;
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

    public String getPersonalInfo() {
        return personalInfo;
    }
}
