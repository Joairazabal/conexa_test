package com.conexa.service.impl;

import com.conexa.dto.*;
import com.conexa.service.ConsumerService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class VehicleServiceImplTest {

    @Mock
    private ConsumerService consumerService;

    @InjectMocks
    private VehicleServiceImpl vehicleServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllVehicles() {
        Map<String, String> uriVariables = new HashMap<>();
        when(consumerService.getExternalApiDataWithPages(any(String.class), any(Map.class), any(Class.class))).thenReturn(new PageDTO());

        PageDTO<VehiclesDTO> result = vehicleServiceImpl.getAllVehicles(uriVariables);

        assertNotNull(result);
    }

    @Test
    public void testGetVehiclesDetailById() {
        String idVehicle = "1";

        when(consumerService.getExternalApiData("/vehicles/" + idVehicle, null, ResponseDetailDTO.class)).thenThrow(new RuntimeException("Error from external API"));

        assertThrows(RuntimeException.class, () -> vehicleServiceImpl.getVehiclesDetailById(idVehicle));
    }

    @Test
    public void testGetVehiclesDetailByName() {
        HashMap<String, String> uriVariables = new HashMap<>();
        when(consumerService.getExternalApiData(any(String.class), any(Map.class), any(Class.class))).thenReturn(new ResponseDetailDTO<>());

        ResponseDetailDTO<List<ResultDetailDTO<PropertiesVehiclesDTO>>> result = vehicleServiceImpl.getVehiclesDetailByName(uriVariables);

        assertNotNull(result);
    }
}