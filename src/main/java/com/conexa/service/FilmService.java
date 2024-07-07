package com.conexa.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.conexa.dto.ResponseFilmDTO;

@Service
public interface FilmService {

    public ResponseFilmDTO getFilms(Map<String, String> uriVariables);

}