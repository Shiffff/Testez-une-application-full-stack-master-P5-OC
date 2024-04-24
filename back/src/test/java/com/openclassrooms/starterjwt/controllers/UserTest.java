package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserTest {

    private UserController userController;
    private UserService userService;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService, userMapper);
    }

    @Test
    void findById_UserExists_ReturnsUserDto() {
        // Given
        Long id = 1L;
        User user = createUser();
        UserDto userDto = createUserDto();

        when(userService.findById(id)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        // When
        ResponseEntity<?> response = userController.findById(String.valueOf(id));

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void findById_UserDoesNotExist_ReturnsNotFound() {
        // Given
        Long id = 1L;
        when(userService.findById(id)).thenReturn(null);

        // When
        ResponseEntity<?> response = userController.findById(String.valueOf(id));

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findById_InvalidId_ReturnsBadRequest() {
        // When
        ResponseEntity<?> response = userController.findById("abc");

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void save_ValidUserIdAndAuthenticatedUser_DeletesUser() {
        // Given
        Long id = 1L;
        User user = createUser();
        UserDetails userDetails = createUserDetails(user.getEmail());
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );

        when(userService.findById(id)).thenReturn(user);
        when(userDetails.getUsername()).thenReturn(user.getEmail());

        // When
        ResponseEntity<?> response = userController.save(String.valueOf(id));

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).delete(id);
    }

    @Test
    void save_ValidUserIdAndDifferentAuthenticatedUser_ReturnsUnauthorized() {
        // Given
        Long id = 1L;
        User user = createUser();
        UserDetails userDetails = createUserDetails("different@example.com");
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );

        when(userService.findById(id)).thenReturn(user);
        when(userDetails.getUsername()).thenReturn("different@example.com");

        // When
        ResponseEntity<?> response = userController.save(String.valueOf(id));

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(userService, never()).delete(id);
    }

    @Test
    void save_UserNotFound_ReturnsNotFound() {
        // Given
        Long id = 1L;
        when(userService.findById(id)).thenReturn(null);

        // When
        ResponseEntity<?> response = userController.save(String.valueOf(id));

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, never()).delete(id);
    }

    @Test
    void save_InvalidUserId_ReturnsBadRequest() {
        // When
        ResponseEntity<?> response = userController.save("abc");

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, never()).delete(any());
    }

    private User createUser() {
        return new User(
                1L,
                "john.doe@example.com",
                "Doe",
                "John",
                "password",
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    private UserDto createUserDto() {
        return new UserDto(
                1L,
                "john.doe@example.com",
                "Doe",
                "John",
                true,
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    private UserDetails createUserDetails(String username) {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);
        return userDetails;
    }
}
