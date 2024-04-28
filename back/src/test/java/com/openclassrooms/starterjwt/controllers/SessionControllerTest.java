package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionMapper sessionMapper;

    private SessionDto sessionDto;

    @BeforeEach
    public void setUp() throws Exception {
        sessionDto = new SessionDto();
        sessionDto.setName("Test Session");
        sessionDto.setDescription("Test description");
        sessionDto.setTeacher_id(1L);
        sessionDto.setDate(new Date());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/session")
                        .content(objectMapper.writeValueAsString(sessionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        SessionDto createdSession = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SessionDto.class);
        sessionDto.setId(createdSession.getId());
    }

    @Test
    @WithMockUser
    public void testFindSessionById_WithValidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/session/{id}", sessionDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(sessionDto.getName()));
    }

    @Test
    @WithMockUser
    public void testFindAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/session")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testUpdate() throws Exception {
        SessionDto updatedSessionDto = new SessionDto();
        updatedSessionDto.setId(sessionDto.getId());
        updatedSessionDto.setName("Updated Test Session");
        updatedSessionDto.setDescription("Updated Test description");
        updatedSessionDto.setTeacher_id(1L);
        updatedSessionDto.setDate(new Date());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/session/{id}", sessionDto.getId())
                        .content(objectMapper.writeValueAsString(updatedSessionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedSessionDto.getName()))
                .andExpect(jsonPath("$.description").value(updatedSessionDto.getDescription()));
    }

    @Test
    @WithMockUser
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/session/{id}", sessionDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/session/{id}", sessionDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
