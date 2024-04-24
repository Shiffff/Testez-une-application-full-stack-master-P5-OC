package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TeacherTest {

    private TeacherController teacherController;
    private TeacherService teacherService;
    private TeacherMapper teacherMapper;

    @BeforeEach
    void setUp() {
        teacherService = mock(TeacherService.class);
        teacherMapper = mock(TeacherMapper.class);
        teacherController = new TeacherController(teacherService, teacherMapper);
    }

    @Test
    void findById_TeacherExists_ReturnsTeacherDto() {
        // Given
        Long id = 1L;
        Teacher teacher = createTeacher();
        TeacherDto teacherDto = createTeacherDto();

        when(teacherService.findById(id)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);

        // When
        ResponseEntity<?> response = teacherController.findById(String.valueOf(id));

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teacherDto, response.getBody());
    }

    @Test
    void findById_TeacherDoesNotExist_ReturnsNotFound() {
        // Given
        Long id = 1L;
        when(teacherService.findById(id)).thenReturn(null);

        // When
        ResponseEntity<?> response = teacherController.findById(String.valueOf(id));

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findById_InvalidId_ReturnsBadRequest() {
        // When
        ResponseEntity<?> response = teacherController.findById("abc");

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void findAll_ReturnsListOfTeacherDto() {
        // Given
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(createTeacher());
        teachers.add(createTeacher());

        List<TeacherDto> teacherDtos = new ArrayList<>();
        teacherDtos.add(createTeacherDto());
        teacherDtos.add(createTeacherDto());

        when(teacherService.findAll()).thenReturn(teachers);
        when(teacherMapper.toDto(teachers)).thenReturn(teacherDtos);

        // When
        ResponseEntity<?> response = teacherController.findAll();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teacherDtos, response.getBody());
    }

    private Teacher createTeacher() {
        return new Teacher(
                1L,
                "Doe",
                "John",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    private TeacherDto createTeacherDto() {
        return new TeacherDto(
                1L,
                "Doe",
                "John",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
