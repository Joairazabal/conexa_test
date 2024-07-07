package com.conexa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.conexa.dto.PageDTO;
import com.conexa.dto.PropertiesVehiclesDTO;
import com.conexa.dto.ResponseDetailDTO;
import com.conexa.dto.ResultDetailDTO;
import com.conexa.dto.VehiclesDTO;

@Service
public interface VehicleService {

    public PageDTO<VehiclesDTO> getAllVehicles(Map<String, String> uriVariables);

    public ResponseDetailDTO<ResultDetailDTO<PropertiesVehiclesDTO>> getVehiclesDetailById(
            String idStarship);

    public ResponseDetailDTO<List<ResultDetailDTO<PropertiesVehiclesDTO>>> getVehiclesDetailByName(
            HashMap<String, String> uriVariables);

}
