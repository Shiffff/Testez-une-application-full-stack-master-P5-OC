package com.openclassrooms.starterjwt.security.services;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

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

    @Test
    public void testFirstName() {
        // Given
        String firstName = "John";
        UserDetailsImpl user = UserDetailsImpl.builder().firstName(firstName).build();

        // When
        String retrievedFirstName = user.getFirstName();

        // Then
        assertEquals(firstName, retrievedFirstName);
    }

    @Test
    public void testLastName() {
        // Given
        String lastName = "Doe";
        UserDetailsImpl user = UserDetailsImpl.builder().lastName(lastName).build();

        // When
        String retrievedLastName = user.getLastName();

        // Then
        assertEquals(lastName, retrievedLastName);
    }

    @Test
    public void testAdmin() {
        // Given
        Boolean admin = true;
        UserDetailsImpl user = UserDetailsImpl.builder().admin(admin).build();

        // When
        Boolean retrievedAdmin = user.getAdmin();

        // Then
        assertEquals(admin, retrievedAdmin);
    }
    @Test
    public void testId() {
        // Given
        Long id = 123L;
        UserDetailsImpl user = UserDetailsImpl.builder().id(id).build();

        // When
        Long retrievedId = user.getId();

        // Then
        assertEquals(id, retrievedId);
    }

    @Test
    public void testPassword() {
        // Given
        String password = "secret";
        UserDetailsImpl user = UserDetailsImpl.builder().password(password).build();

        // When
        String retrievedPassword = user.getPassword();

        // Then
        assertEquals(password, retrievedPassword);
    }

    @Test
    public void testGetAuthorities_ReturnsEmptySet() {
        // Given
        UserDetailsImpl user = UserDetailsImpl.builder().build();

        // When
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // Then
        assertTrue(authorities.isEmpty());
    }


}
