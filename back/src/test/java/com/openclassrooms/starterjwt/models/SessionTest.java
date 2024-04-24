package com.openclassrooms.starterjwt.models;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;

public class SessionTest {

    private Session session;
    private int sessionHashcode;

    @BeforeEach
    public void setUp() {
        session = createSession();
        sessionHashcode = session.hashCode();
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

    @Test
    public void testSessionNameValidation() {
        session.setName("Session Name");
        assertThat(session.getName()).isEqualTo("Session Name");
    }

    @Test
    public void testSessionDateNotNull() {
        session.setDate(new Date());
        assertThat(session.getDate()).isNotNull();
    }

    @Test
    public void testSessionDescriptionSize() {
        session.setDescription("Session Description");
        assertThat(session.getDescription()).isEqualTo("Session Description");
    }

    @Test
    public void testSettersAndGetters() {
        Long id = 1L;
        String name = "Session Name";
        Date date = new Date();
        String description = "Session Description";
        Teacher teacher = new Teacher();
        ArrayList<User> users = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        session.setId(id);
        session.setName(name);
        session.setDate(date);
        session.setDescription(description);
        session.setTeacher(teacher);
        session.setUsers(users);
        session.setCreatedAt(createdAt);
        session.setUpdatedAt(updatedAt);

        assertThat(session.getId()).isEqualTo(id);
        assertThat(session.getName()).isEqualTo(name);
        assertThat(session.getDate()).isEqualTo(date);
        assertThat(session.getDescription()).isEqualTo(description);
        assertThat(session.getTeacher()).isEqualTo(teacher);
        assertThat(session.getUsers()).isEqualTo(users);
        assertThat(session.getCreatedAt()).isEqualTo(createdAt);
        assertThat(session.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void testToString() {
        assertThat(session.toString().contains("Session Name")).isTrue();
        assertThat(session.toString().contains("Session Description")).isTrue();
    }
    @Test
    public void testEquals() {
        Session session1 = createSession();
        Session session2 = createSession();
        assertThat(session1.equals(session2)).isTrue();
    }

    @Test
    public void testBuilder() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Session session = Session.builder()
                .id(1L)
                .name("Sample Session Name")
                .date(new Date())
                .description("Sample Session Description")
                .teacher(new Teacher())
                .users(new ArrayList<>())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        assertThat(session.getId()).isEqualTo(1L);
        assertThat(session.getName()).isEqualTo("Sample Session Name");
        assertThat(session.getDate()).isNotNull();
        assertThat(session.getDescription()).isEqualTo("Sample Session Description");
        assertThat(session.getTeacher()).isNotNull();
        assertThat(session.getUsers()).isNotNull();
        assertThat(session.getCreatedAt()).isEqualTo(createdAt);
        assertThat(session.getUpdatedAt()).isEqualTo(updatedAt);
    }


    @Test
    public void testHashCodes() {
        assertThat(session.hashCode()).isEqualTo(sessionHashcode);
    }
}