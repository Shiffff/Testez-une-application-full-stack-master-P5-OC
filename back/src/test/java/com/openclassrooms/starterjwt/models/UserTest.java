package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class UserTest {

    private User user;
    private int userHashcode;

    @BeforeEach
    public void setUp() {
        user = createUser();
        userHashcode = user.hashCode();
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

    @Test
    public void testEmailValidation() {
        user.setEmail("test@example.com");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
    }
    @Test
    public void testLastNameValidation() {
        user.setLastName("Doe");
        assertThat(user.getLastName()).isEqualTo("Doe");
    }

    @Test
    public void testFirstNameValidation() {
        user.setFirstName("John");
        assertThat(user.getFirstName()).isEqualTo("John");
    }

    @Test
    public void testPasswordValidation() {
        user.setPassword("password");
        assertThat(user.getPassword()).isEqualTo("password");
    }

    @Test
    public void testAdminFlag() {
        user.setAdmin(true);
        assertThat(user.isAdmin()).isTrue();
    }

    @Test
    public void testSettersAndGetters() {
        Long id = 1L;
        String email = "test@example.com";
        String lastName = "Doe";
        String firstName = "John";
        String password = "password";
        boolean admin = false;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        user.setId(id);
        user.setEmail(email);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setPassword(password);
        user.setAdmin(admin);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getLastName()).isEqualTo(lastName);
        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.isAdmin()).isEqualTo(admin);
        assertThat(user.getCreatedAt()).isEqualTo(createdAt);
        assertThat(user.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void testToString() {
        assertThat(user.toString().contains("test@example.com")).isTrue();
        assertThat(user.toString().contains("Doe")).isTrue();
        assertThat(user.toString().contains("John")).isTrue();
    }

    @Test
    public void testEquals() {
        User user1 = createUser();
        User user2 = createUser();
        assertThat(user1.equals(user2)).isTrue();
    }

    @Test
    public void testBuilder() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .lastName("Doe")
                .firstName("John")
                .password("password")
                .admin(false)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.isAdmin()).isFalse();
        assertThat(user.getCreatedAt()).isEqualTo(createdAt);
        assertThat(user.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    public void testHashCodes() {
        assertThat(user.hashCode()).isEqualTo(userHashcode);
    }

}
