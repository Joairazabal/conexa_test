package com.conexa.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conexa.dto.PropertiesVehiclesDTO;
import com.conexa.dto.ResponseDetailDTO;
import com.conexa.dto.ResultDetailDTO;
import com.conexa.service.VehicleService;

@RestController
@RequestMapping("/api/v1")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles")
    public ResponseEntity<?> getAllVehicles(@RequestParam HashMap<String, String> uriVariables) {
        if (uriVariables.containsKey("name") && !uriVariables.get("name").isEmpty()) {
            return ResponseEntity.ok().body(this.vehicleService.getVehiclesDetailByName(uriVariables));
        }
        return ResponseEntity.ok().body(this.vehicleService.getAllVehicles(uriVariables));
    }

    @GetMapping("/admin/vehicles/{id}")
    public ResponseEntity<ResponseDetailDTO<ResultDetailDTO<PropertiesVehiclesDTO>>> getDetailVehicleById(
            @PathVariable String id) {

        return ResponseEntity.ok().body(this.vehicleService.getVehiclesDetailById(id));

    }
}
