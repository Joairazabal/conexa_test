package com.test.conexa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.conexa.dto.PropertiesFilmDTO;
import com.conexa.dto.ResponseFilmDTO;
import com.conexa.dto.ResultFilmDTO;
import com.conexa.security.payload.LoginRequestDTO;
import com.conexa.security.payload.RegisterRequestDTO;
import com.conexa.service.ConsumerService;
import com.conexa.service.impl.FilmServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FilmService {

    @Mock
    private ConsumerService consumerService;

    @InjectMocks
    private FilmServiceImpl filmServiceImpl;

    private ResponseFilmDTO mockResponseFilmDTO;

    @BeforeEach
    void setUp() {
        mockResponseFilmDTO = new ResponseFilmDTO();

        ResultFilmDTO result = new ResultFilmDTO();
        PropertiesFilmDTO properties = new PropertiesFilmDTO();
        properties.setTitle("A New Hope");

        result.set_id("1");
        result.setProperties(properties);

        List<ResultFilmDTO> results = new ArrayList<>();
        results.add(result);

        mockResponseFilmDTO.setResult(results);
    }

    @Test
    void testGetFilms() {
        Map<String, String> uriVariables = new HashMap<>();

        when(consumerService.getExternalApiData(eq("/films"), eq(uriVariables), eq(ResponseFilmDTO.class)))
                .thenReturn(mockResponseFilmDTO);

        ResponseFilmDTO response = filmServiceImpl.getFilms(uriVariables);

        assertEquals(mockResponseFilmDTO, response);

        assertEquals("A New Hope", response.getResult().get(0).getProperties().getTitle());
        assertEquals("1", response.getResult().get(0).get_id());
    }

    
}
