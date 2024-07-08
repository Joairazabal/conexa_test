package com.conexa.service.impl;

import com.conexa.dto.*;
import com.conexa.service.ConsumerService;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class StarShipServiceImplTest {

    @Mock
    private ConsumerService consumerService;

    @InjectMocks
    private StarShipServiceImpl starShipServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetStarShip() {
        Map<String, String> uriVariables = new HashMap<>();
        when(consumerService.getExternalApiDataWithPages(any(String.class), any(Map.class), any(Class.class))).thenReturn(new PageDTO());

        PageDTO<StarshipDTO> result = starShipServiceImpl.getStarShip(uriVariables);

        assertNotNull(result);
    }

    @Test
    public void testGetDetailStarshipByName() {
        HashMap<String, String> uriVariables = new HashMap<>();
        when(consumerService.getExternalApiData(any(String.class), any(Map.class), any(Class.class))).thenReturn(new ResponseDetailDTO<>());

        ResponseDetailDTO<List<ResultDetailDTO<PropertiesStarshipDTO>>> result = starShipServiceImpl.getDetailStarshipByName(uriVariables);

        assertNotNull(result);
    }

    @Test
    public void testGetDetailStarshipById() {
        String idStarship = "1";

        when(consumerService.getExternalApiData("/starships/" + idStarship, null, ResponseDetailDTO.class))
                .thenThrow(new RuntimeException("Error from external API"));

        assertThrows(RuntimeException.class, () -> starShipServiceImpl.getDetailStarshipById(idStarship));
    }
}
