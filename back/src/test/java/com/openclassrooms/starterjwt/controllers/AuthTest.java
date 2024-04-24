package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthTest {

    private static final String AUTH_API_ROOT = "/api/auth";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private UserRepository userRepository;

    private static User testUser;

    @BeforeAll
    static void setUp() {
        testUser = new User(1L, "test@test.com", "user-lastname", "user-firstname",
                "123456", false, LocalDateTime.now(), null);
    }

    @Test
    void login_WhenValidCredentials_ExpectToken() throws Exception {
        // Given
        UserDetailsImpl userDetails = new UserDetailsImpl(testUser.getId(), testUser.getEmail(),
                testUser.getFirstName(), testUser.getLastName(), testUser.isAdmin(), testUser.getPassword());
        Authentication authentication = getAuthenticationMock(userDetails);

        // When
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(any(Authentication.class))).thenReturn("jwt");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

        // Then
        mockMvc.perform(post(AUTH_API_ROOT + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@test.com\", \"password\": \"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is("jwt")));
    }

    @Test
    void login_WhenInvalidCredentials_ExpectUnauthorized() throws Exception {
        // Given
        when(authenticationManager.authenticate(any())).thenThrow(new MyAuthenticationException("Not authorized"));

        // Then
        mockMvc.perform(post(AUTH_API_ROOT + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@test.com\", \"password\": \"123456\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void register_WhenUserNotExists_ExpectSuccessMessage() throws Exception {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        // Then
        mockMvc.perform(post(AUTH_API_ROOT + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"email\": \"test@test.com\", " +
                                "\"firstName\": \"user-firstname\", " +
                                "\"lastName\": \"user-lastname\", " +
                                "\"password\": \"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("User registered successfully!")));
    }

    @Test
    void register_WhenUserExists_ExpectErrorMessage() throws Exception {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Then
        mockMvc.perform(post(AUTH_API_ROOT + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"email\": \"test@test.com\", " +
                                "\"firstName\": \"user-firstname\", " +
                                "\"lastName\": \"user-lastname\", " +
                                "\"password\": \"123456\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Email is already taken!")));
    }

    private Authentication getAuthenticationMock(UserDetailsImpl userDetails) {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return userDetails;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        };
    }
    class MyAuthenticationException extends AuthenticationException {
        public MyAuthenticationException(String msg) {
            super(msg);
        }
    }

}

