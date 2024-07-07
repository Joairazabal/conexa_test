package com.conexa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.conexa.dto.PageDTO;
import com.conexa.dto.PropertiesVehiclesDTO;
import com.conexa.dto.ResponseDetailDTO;
import com.conexa.dto.ResultDetailDTO;
import com.conexa.dto.VehiclesDTO;
import com.conexa.service.ConsumerService;
import com.conexa.service.VehicleService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class VehicleServiceImpl implements VehicleService {

    private final ConsumerService consumerService;

    @Override
    public PageDTO<VehiclesDTO> getAllVehicles(Map<String, String> uriVariables) {
        return this.consumerService.getExternalApiDataWithPages("/vehicles", uriVariables, VehiclesDTO.class);
    }

    @Override
    public ResponseDetailDTO<ResultDetailDTO<PropertiesVehiclesDTO>> getVehiclesDetailById(String idStarship) {
        return this.consumerService.getExternalApiData("/vehicles/" + idStarship, null, ResponseDetailDTO.class);
    }

    @Override
    public ResponseDetailDTO<List<ResultDetailDTO<PropertiesVehiclesDTO>>> getVehiclesDetailByName(
            HashMap<String, String> uriVariables) {
        // TODO Auto-generated method stub
        return this.consumerService.getExternalApiData("/vehicles/", uriVariables, ResponseDetailDTO.class);

    }
}
