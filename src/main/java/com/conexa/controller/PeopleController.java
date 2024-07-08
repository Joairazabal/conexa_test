package com.conexa.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conexa.dto.PropertiesPeopleDTO;
import com.conexa.dto.ResponseDetailDTO;
import com.conexa.dto.ResultDetailDTO;
import com.conexa.service.PeopleService;

@RestController
@RequestMapping("/api/v1")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @GetMapping("/people")
    public Object getPeople(@RequestParam(required = false) Map<String, String> uriParams) {
        if (uriParams.containsKey("name") && !uriParams.get("name").isEmpty()) {
            return this.peopleService.getPeopleDetailByName(uriParams);
        }
        return this.peopleService.getAllPeople(uriParams);
    }

    @GetMapping("/admin/people/{id}")
    public ResponseEntity<ResponseDetailDTO<ResultDetailDTO<PropertiesPeopleDTO>>> getPeopleById(
            @PathVariable String id) {
        return ResponseEntity.ok().body(this.peopleService.getPeopleDetailById(id));
    }
}
