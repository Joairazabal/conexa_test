package com.conexa.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conexa.dto.PropertiesStarshipDTO;
import com.conexa.dto.ResponseDetailDTO;
import com.conexa.dto.ResultDetailDTO;
import com.conexa.service.StarShipService;

@RestController
@RequestMapping("/api/v1")
public class StarShipController {

    @Autowired
    private StarShipService starShipsService;

    @GetMapping("/starships")
    public ResponseEntity<Object> getStarships(@RequestParam HashMap<String, String> uriVariables) {

        if (uriVariables.containsKey("name") && !uriVariables.get("name").isEmpty()) {
            return ResponseEntity.ok(this.starShipsService.getDetailStarshipByName(uriVariables));
        }
        return ResponseEntity.ok().body(this.starShipsService.getStarShip(uriVariables));
    }

    @GetMapping("/admin/starships/{id}")
    public ResponseEntity<ResponseDetailDTO<ResultDetailDTO<PropertiesStarshipDTO>>> getDetailStarship(
            @PathVariable String id) {
        return ResponseEntity.ok().body(this.starShipsService.getDetailStarshipById(id));
    }

}
