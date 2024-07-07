package com.conexa.dto;

import lombok.Data;

@Data
public class ResponseDetailDTO<P> {

    private String message;

    private P result;
}
