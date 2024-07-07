package com.conexa.dto;

import java.util.List;

import lombok.Data;

@Data
public class PropertiesFilmDTO {

    private List<String> characters;
    private List<String> planets;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> species;
    private String created;
    private String edited;
    private String producer;
    private String title;
    private int episode_id;
    private String director;
    private String release_date;
    private String opening_crawl;
    private String url;
}
