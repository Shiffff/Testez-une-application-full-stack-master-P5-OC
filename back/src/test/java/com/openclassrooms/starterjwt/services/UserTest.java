package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void testDeleteUser() {
        // Given
        Long userId = 1L;

        // When
        userService.delete(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testFindUserById() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Then
        User foundUser = userService.findById(userId);
        assertThat(foundUser).isEqualTo(user);
        verify(userRepository, times(1)).findById(userId);
    }
}
