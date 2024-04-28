package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

public class TeacherModelsTest {

    private Teacher teacher;

    @BeforeEach
    public void setUp() {
        teacher = createTeacher();
    }

    private Teacher createTeacher() {
        Long id = 1L;
        String lastName = "Doe";
        String firstName = "John";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        return new Teacher(id, lastName, firstName, createdAt, updatedAt);
    }


}
