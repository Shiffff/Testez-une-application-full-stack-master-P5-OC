package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class TeacherTest {

    private Teacher teacher;
    private int teacherHashcode;

    @BeforeEach
    public void setUp() {
        teacher = createTeacher();
        teacherHashcode = teacher.hashCode();
    }

    private Teacher createTeacher() {
        Long id = 1L;
        String lastName = "Doe";
        String firstName = "John";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        return new Teacher(id, lastName, firstName, createdAt, updatedAt);
    }

    @Test
    public void testLastNameValidation() {
        teacher.setLastName("Doe");
        assertThat(teacher.getLastName()).isEqualTo("Doe");
    }

    @Test
    public void testFirstNameValidation() {
        teacher.setFirstName("John");
        assertThat(teacher.getFirstName()).isEqualTo("John");
    }

    @Test
    public void testSettersAndGetters() {
        Long id = 1L;
        String lastName = "Doe";
        String firstName = "John";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        teacher.setId(id);
        teacher.setLastName(lastName);
        teacher.setFirstName(firstName);
        teacher.setCreatedAt(createdAt);
        teacher.setUpdatedAt(updatedAt);

        assertThat(teacher.getId()).isEqualTo(id);
        assertThat(teacher.getLastName()).isEqualTo(lastName);
        assertThat(teacher.getFirstName()).isEqualTo(firstName);
        assertThat(teacher.getCreatedAt()).isEqualTo(createdAt);
        assertThat(teacher.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void testToString() {
        assertThat(teacher.toString().contains("Doe")).isTrue();
        assertThat(teacher.toString().contains("John")).isTrue();
    }

    @Test
    public void testEquals() {
        Teacher teacher1 = createTeacher();
        Teacher teacher2 = createTeacher();
        assertThat(teacher1.equals(teacher2)).isTrue();
    }

    @Test
    public void testBuilder() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Teacher teacher = Teacher.builder()
                .id(1L)
                .lastName("Doe")
                .firstName("John")
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        assertThat(teacher.getId()).isEqualTo(1L);
        assertThat(teacher.getLastName()).isEqualTo("Doe");
        assertThat(teacher.getFirstName()).isEqualTo("John");
        assertThat(teacher.getCreatedAt()).isEqualTo(createdAt);
        assertThat(teacher.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    public void testHashCodes() {
        assertThat(teacher.hashCode()).isEqualTo(teacherHashcode);
    }

}
