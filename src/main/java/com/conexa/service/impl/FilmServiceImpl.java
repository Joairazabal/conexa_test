package com.conexa.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.conexa.dto.ResponseFilmDTO;
import com.conexa.service.ConsumerService;
import com.conexa.service.FilmService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {

    private final ConsumerService consumerService;

    @Override
    public ResponseFilmDTO getFilms(Map<String, String> uriVariables) {
        return this.consumerService.getExternalApiData("/films", uriVariables, ResponseFilmDTO.class);
    }

}
