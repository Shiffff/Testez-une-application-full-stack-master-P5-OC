package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TeacherTest {

    @Mock
    private TeacherRepository teacherRepository;

    private TeacherService teacherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        teacherService = new TeacherService(teacherRepository);
    }

    @Test
    public void testFindAllTeachers() {
        // Given
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher());
        teachers.add(new Teacher());

        // When
        when(teacherRepository.findAll()).thenReturn(teachers);

        // Then
        List<Teacher> foundTeachers = teacherService.findAll();
        assertThat(foundTeachers.size()).isEqualTo(2);
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    public void testFindTeacherById() {
        // Given
        Long teacherId = 1L;
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);

        // When
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        // Then
        Teacher foundTeacher = teacherService.findById(teacherId);
        assertThat(foundTeacher).isEqualTo(teacher);
        verify(teacherRepository, times(1)).findById(teacherId);
    }
}
