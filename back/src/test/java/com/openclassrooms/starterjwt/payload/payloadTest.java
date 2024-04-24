package com.openclassrooms.starterjwt.payload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;

public class payloadTest {

    @Test
    public void testLoginRequest() {
        // Créer un objet LoginRequest
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        // Vérifier si les valeurs sont correctement initialisées
        assertNotNull(loginRequest.getEmail());
        assertNotNull(loginRequest.getPassword());
        assertEquals("test@example.com", loginRequest.getEmail());
        assertEquals("password", loginRequest.getPassword());
    }

    @Test
    public void testSignupRequest() {
        // Créer un objet SignupRequest
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password");

        // Vérifier si les valeurs sont correctement initialisées
        assertNotNull(signupRequest.getEmail());
        assertNotNull(signupRequest.getFirstName());
        assertNotNull(signupRequest.getLastName());
        assertNotNull(signupRequest.getPassword());
        assertEquals("test@example.com", signupRequest.getEmail());
        assertEquals("John", signupRequest.getFirstName());
        assertEquals("Doe", signupRequest.getLastName());
        assertEquals("password", signupRequest.getPassword());
    }

    @Test
    public void testJwtResponse() {
        // Créer un objet JwtResponse
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "username", "John", "Doe", true);

        // Vérifier si les valeurs sont correctement initialisées
        assertNotNull(jwtResponse.getToken());
        assertNotNull(jwtResponse.getId());
        assertNotNull(jwtResponse.getUsername());
        assertNotNull(jwtResponse.getFirstName());
        assertNotNull(jwtResponse.getLastName());
        assertNotNull(jwtResponse.getAdmin());
        assertEquals("token", jwtResponse.getToken());
        assertEquals(1L, jwtResponse.getId().longValue());
        assertEquals("username", jwtResponse.getUsername());
        assertEquals("John", jwtResponse.getFirstName());
        assertEquals("Doe", jwtResponse.getLastName());
        assertEquals(true, jwtResponse.getAdmin());
    }

    @Test
    public void testMessageResponse() {
        // Créer un objet MessageResponse
        MessageResponse messageResponse = new MessageResponse("Test Message");

        // Vérifier si la valeur est correctement initialisée
        assertNotNull(messageResponse.getMessage());
        assertEquals("Test Message", messageResponse.getMessage());
    }
}
