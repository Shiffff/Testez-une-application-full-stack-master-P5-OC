package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherMapper teacherMapper;

    private TeacherDto teacherDto;

    @BeforeEach
    public void setUp() {

        Teacher teacher = new Teacher()
                .setFirstName("John")
                .setLastName("Doe")
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());
        teacher = this.teacherRepository.save(teacher);

        teacherDto = teacherMapper.toDto(teacher);
    }

    @Test
    @WithMockUser
    public void testFindTeacherById_WithValidId() throws Exception {
        Long validId = teacherDto.getId();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/teacher/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        TeacherDto responseTeacherDto = objectMapper.readValue(content, TeacherDto.class);
        assertThat(responseTeacherDto).isNotNull();
        assertThat(responseTeacherDto.getId()).isEqualTo(validId);
        assertThat(responseTeacherDto.getFirstName()).isEqualTo(teacherDto.getFirstName());
        assertThat(responseTeacherDto.getLastName()).isEqualTo(teacherDto.getLastName());
    }

    @Test
    @WithMockUser
    public void testFindTeacherById_WithInvalidId() throws Exception {
        Long invalidId = 999L;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/teacher/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser
    public void testFindAllTeachers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/teacher")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        List<TeacherDto> responseTeachers = Arrays.asList(objectMapper.readValue(content, TeacherDto[].class));
        assertThat(responseTeachers).isNotEmpty();
    }

}
