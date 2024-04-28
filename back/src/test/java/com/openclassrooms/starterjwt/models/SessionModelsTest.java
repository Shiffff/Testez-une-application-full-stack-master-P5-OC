package com.openclassrooms.starterjwt.models;


import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class SessionModelsTest {

    private Session session;

    @BeforeEach
    public void setUp() {
        session = createSession();
    }


    private Session createSession() {
        Long id = 1L;
        String name = "Session Name";
        Date date = new Date();
        String description = "Session Description";
        Teacher teacher = new Teacher();
        ArrayList<User> users = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        return new Session(id, name, date, description, teacher, users, createdAt, updatedAt);
    }

}