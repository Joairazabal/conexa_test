package com.conexa.dto;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class PropertiesVehiclesDTO {

    private String height;
    private String mass;
    private String hairColor;
    private String skinColor;
    private String eyeColor;
    private String birthYear;
    private String gender;
    private OffsetDateTime created;
    private OffsetDateTime edited;
    private String name;
    private String homeworld;
    private String url;
}
