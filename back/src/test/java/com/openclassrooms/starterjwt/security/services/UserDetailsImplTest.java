package com.openclassrooms.starterjwt.security.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsImplTest {

    @Test
    public void testEquals_SameObjects_ReturnsTrue() {
        // Given
        UserDetailsImpl user1 = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl user2 = UserDetailsImpl.builder().id(1L).build();

        // When - Then
        assertTrue(user1.equals(user2));
        assertTrue(user2.equals(user1));
    }

    @Test
    public void testEquals_DifferentObjects_ReturnsFalse() {
        // Given
        UserDetailsImpl user1 = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl user2 = UserDetailsImpl.builder().id(2L).build();

        // When - Then
        assertFalse(user1.equals(user2));
        assertFalse(user2.equals(user1));
    }

    @Test
    public void testAccountNonExpired_ReturnsTrue() {
        // Given
        UserDetailsImpl user = UserDetailsImpl.builder().build();

        // When - Then
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    public void testAccountNonLocked_ReturnsTrue() {
        // Given
        UserDetailsImpl user = UserDetailsImpl.builder().build();

        // When - Then
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void testCredentialsNonExpired_ReturnsTrue() {
        // Given
        UserDetailsImpl user = UserDetailsImpl.builder().build();

        // When - Then
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    public void testEnabled_ReturnsTrue() {
        // Given
        UserDetailsImpl user = UserDetailsImpl.builder().build();

        // When - Then
        assertTrue(user.isEnabled());
    }
}
