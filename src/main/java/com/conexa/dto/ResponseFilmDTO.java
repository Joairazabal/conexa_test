package com.conexa.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseFilmDTO {

    private String message;

    private List<ResultFilmDTO> result;
}
