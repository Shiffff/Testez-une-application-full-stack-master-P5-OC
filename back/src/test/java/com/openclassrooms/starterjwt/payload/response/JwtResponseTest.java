package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtResponseTest {

    @Test
    void getToken() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        assertThat(jwtResponse.getToken()).isEqualTo("token");
    }

    @Test
    void getType() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        assertThat(jwtResponse.getType()).isEqualTo("Bearer");
    }

    @Test
    void getId() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        assertThat(jwtResponse.getId()).isEqualTo(1L);
    }

    @Test
    void getUsername() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        assertThat(jwtResponse.getUsername()).isEqualTo("username");
    }

    @Test
    void getFirstName() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        assertThat(jwtResponse.getFirstName()).isEqualTo("John");
    }

    @Test
    void getLastName() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        assertThat(jwtResponse.getLastName()).isEqualTo("Doe");
    }

    @Test
    void setToken() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        jwtResponse.setToken("newToken");
        assertThat(jwtResponse.getToken()).isEqualTo("newToken");
    }

    @Test
    void setType() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        jwtResponse.setType("NewType");
        assertThat(jwtResponse.getType()).isEqualTo("NewType");
    }

    @Test
    void setId() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        jwtResponse.setId(2L);
        assertThat(jwtResponse.getId()).isEqualTo(2L);
    }

    @Test
    void setUsername() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        jwtResponse.setUsername("newUsername");
        assertThat(jwtResponse.getUsername()).isEqualTo("newUsername");
    }

    @Test
    void setFirstName() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        jwtResponse.setFirstName("Jane");
        assertThat(jwtResponse.getFirstName()).isEqualTo("Jane");
    }

    @Test
    void setLastName() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        jwtResponse.setLastName("Smith");
        assertThat(jwtResponse.getLastName()).isEqualTo("Smith");
    }

    @Test
    void setAdmin() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        jwtResponse.setAdmin(false);
        assertThat(jwtResponse.getAdmin()).isEqualTo(false);
    }

    @Test
    void getAdmin() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);
        assertThat(jwtResponse.getAdmin()).isEqualTo(true);
    }
}
