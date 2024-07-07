package com.conexa.controller;

import java.util.Map;

import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conexa.dto.ResponseFilmDTO;
import com.conexa.service.FilmService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class FilmsController {

    private final FilmService filmService;

    @GetMapping("/films")
    public ResponseFilmDTO getFilmd(@RequestParam Map<String, String> allParams) {
        return this.filmService.getFilms(allParams);
    }

}
