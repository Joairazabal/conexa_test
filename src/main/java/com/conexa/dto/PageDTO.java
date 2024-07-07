package com.conexa.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageDTO<C> {

    private String message;
    private int total_records;
    private int total_pages;
    private String previous;
    private String next;
    private List<C> results;

}
