package com.conexa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResultDetailDTO<P> {

    private P properties;

    private String description;

    @JsonProperty("_id")
    private String id;

    private String uid;

    @JsonProperty("__v")
    private long v;
}
