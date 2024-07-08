package com.conexa.controller;


import com.conexa.dto.*;
import com.conexa.service.PeopleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleService peopleService;

    private String token;

    @BeforeEach
    public void setUp() {
        token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE3MjA0NjU2NjMsImV4cCI6MTcyMDU1MjA2M30.w1VE6JwiD1h0op8RHw_KJroSe3NDgIEbkWfAIPo4Yne-zRWPQhqgaMm4ATezhupYORx5WoSxqaCyxewd7glU4Q";
    }

    @Test
    public void testGetPeopleWithoutParams() throws Exception {
        // Simula la respuesta del servicio
        PageDTO<PeopleDTO> pageMock = new PageDTO<>();
        pageMock.setPrevious(null);
        pageMock.setNext("https://www.swapi.tech/api/people?page=2&limit=10");

        PeopleDTO luke = new PeopleDTO();
        luke.setUid("1");
        luke.setName("Luke Skywalker");
        luke.setUrl("https://www.swapi.tech/api/people/1");

        PeopleDTO c3po = new PeopleDTO();
        c3po.setUid("2");
        c3po.setName("C-3PO");
        c3po.setUrl("https://www.swapi.tech/api/people/2");

        PeopleDTO r2d2 = new PeopleDTO();
        r2d2.setUid("3");
        r2d2.setName("R2-D2");
        r2d2.setUrl("https://www.swapi.tech/api/people/3");

        pageMock.setResults(Arrays.asList(luke, c3po, r2d2));

        Mockito.when(peopleService.getAllPeople(Mockito.anyMap())).thenReturn(pageMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/people")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetPeopleWithNameParam() throws Exception {
        PropertiesPeopleDTO r2d2 = new PropertiesPeopleDTO();
        r2d2.setName("R2-D2");
        r2d2.setHeight("96");
        r2d2.setMass("32");
        r2d2.setHairColor("n/a");
        r2d2.setSkinColor("white, blue");
        r2d2.setEyeColor("red");
        r2d2.setBirthYear("33BBY");
        r2d2.setGender("n/a");
        r2d2.setCreated("2024-07-08T00:02:16.103Z");
        r2d2.setEdited("2024-07-08T00:02:16.103Z");
        r2d2.setHomeworld("https://www.swapi.tech/api/planets/8");
        r2d2.setUrl("https://www.swapi.tech/api/people/3");

        ResultDetailDTO<PropertiesPeopleDTO> result = new ResultDetailDTO<>();
        result.setUid("3");
        result.setProperties(r2d2);

        List<ResultDetailDTO<PropertiesPeopleDTO>> results = Collections.singletonList(result);

        ResponseDetailDTO<List<ResultDetailDTO<PropertiesPeopleDTO>>> mockResponse = new ResponseDetailDTO<>();
        mockResponse.setMessage("ok");
        mockResponse.setResult(results);


        Mockito.when(peopleService.getPeopleDetailByName(Mockito.anyMap())).thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/people")
                        .param("name", "Luke Skywalker")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"));
    }

    @Test
    public void testGetPeopleById() throws Exception {
        String id = "3";

        PropertiesPeopleDTO r2d2 = new PropertiesPeopleDTO();
        r2d2.setName("R2-D2");
        r2d2.setHeight("96");
        r2d2.setMass("32");
        r2d2.setHairColor("n/a");
        r2d2.setSkinColor("white, blue");
        r2d2.setEyeColor("red");
        r2d2.setBirthYear("33BBY");
        r2d2.setGender("n/a");
        r2d2.setCreated("2024-07-08T00:02:16.103Z");
        r2d2.setEdited("2024-07-08T00:02:16.103Z");
        r2d2.setHomeworld("https://www.swapi.tech/api/planets/8");
        r2d2.setUrl("https://www.swapi.tech/api/people/3");

        ResultDetailDTO<PropertiesPeopleDTO> result = new ResultDetailDTO<>();
        result.setUid("3");
        result.setProperties(r2d2);


        ResponseDetailDTO<ResultDetailDTO<PropertiesPeopleDTO>> mockResponse = new ResponseDetailDTO<>();
        mockResponse.setMessage("ok");
        mockResponse.setResult(result);

        Mockito.when(peopleService.getPeopleDetailById(id)).thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/people/" + id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.properties.name").exists());
        // Add more assertions as needed
    }

    @Test
    public void testForbiddenWithoutToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/people")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


}