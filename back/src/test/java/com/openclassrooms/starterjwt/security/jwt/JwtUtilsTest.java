package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${oc.app.jwtSecret}")
    private String jwtSecret;

    @Value("${oc.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGenerateJwtToken() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("testUser")
                .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        String token = jwtUtils.generateJwtToken(authentication);
        assertThat(token).isNotNull();
    }

    @Test
    public void testGetUserNameFromJwtToken() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("testUser")
                .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        String token = jwtUtils.generateJwtToken(authentication);
        String username = jwtUtils.getUserNameFromJwtToken(token);
        assertThat(username).isEqualTo("testUser");
    }

    @Test
    public void testValidateJwtToken() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("testUser")
                .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        String token = jwtUtils.generateJwtToken(authentication);
        boolean isValid = jwtUtils.validateJwtToken(token);
        assertThat(isValid).isTrue();
    }

    @Test
    public void testValidateJwtToken_WithExpiredToken() {
        String token = "expiredToken";
        boolean isValid = jwtUtils.validateJwtToken(token);
        assertThat(isValid).isFalse();
    }

    @Test
    public void testValidateJwtToken_WithInvalidToken() {
        String token = "invalidToken";
        boolean isValid = jwtUtils.validateJwtToken(token);
        assertThat(isValid).isFalse();
    }

    @Test
    public void testValidateJwtToken_WithUnsupportedToken() {
        String token = "unsupportedToken";
        boolean isValid = jwtUtils.validateJwtToken(token);
        assertThat(isValid).isFalse();
    }

    @Test
    public void testValidateJwtToken_WithMalformedToken() {
        String token = "malformedToken";
        boolean isValid = jwtUtils.validateJwtToken(token);
        assertThat(isValid).isFalse();
    }

    @Test
    public void testValidateJwtToken_WithEmptyToken() {
        String token = "";
        boolean isValid = jwtUtils.validateJwtToken(token);
        assertThat(isValid).isFalse();
    }
}
