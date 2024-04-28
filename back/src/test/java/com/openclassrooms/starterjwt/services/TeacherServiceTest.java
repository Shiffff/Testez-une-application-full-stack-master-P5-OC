package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TeacherServiceTest {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository teacherRepository;

    @AfterEach
    void cleanup() {
        teacherRepository.deleteAll();
    }

    @Test
    void testFindAllTeachers() {

        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("John");
        teacher1.setLastName("Doe");
        teacher1.setCreatedAt(LocalDateTime.now());
        teacher1.setUpdatedAt(LocalDateTime.now());
        teacher1 = teacherRepository.save(teacher1);

        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Jane");
        teacher2.setLastName("Doe");
        teacher2.setCreatedAt(LocalDateTime.now());
        teacher2.setUpdatedAt(LocalDateTime.now());
        teacher2 = teacherRepository.save(teacher2);

        List<Teacher> teachers = teacherService.findAll();

        assertNotNull(teachers);
        assertEquals(2, teachers.size());
    }

    @Test
    void testFindTeacherById() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());
        teacher = teacherRepository.save(teacher);

        Long teacherId = teacher.getId();

        Teacher foundTeacher = teacherService.findById(teacherId);

        assertNotNull(foundTeacher);
        assertEquals(teacher.getFirstName(), foundTeacher.getFirstName());
        assertEquals(teacher.getLastName(), foundTeacher.getLastName());
        assertEquals(teacher.getCreatedAt(), foundTeacher.getCreatedAt());
        assertEquals(teacher.getUpdatedAt(), foundTeacher.getUpdatedAt());
    }
}
