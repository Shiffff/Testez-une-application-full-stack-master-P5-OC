package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

public class UserModelsTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = createUser();
    }

    private User createUser() {
        Long id = 1L;
        String email = "test@example.com";
        String lastName = "Doe";
        String firstName = "John";
        String password = "password";
        boolean admin = false;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        return new User(id, email, lastName, firstName, password, admin, createdAt, updatedAt);
    }

}
