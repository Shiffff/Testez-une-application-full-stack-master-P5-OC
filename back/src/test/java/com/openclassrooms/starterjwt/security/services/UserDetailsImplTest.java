package com.openclassrooms.starterjwt.security.services;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsImplTest {

    @Test
    public void testEquals_SameObjects_ReturnsTrue() {
        UserDetailsImpl user1 = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl user2 = UserDetailsImpl.builder().id(1L).build();

        assertTrue(user1.equals(user2));
        assertTrue(user2.equals(user1));
    }

    @Test
    public void testEquals_DifferentObjects_ReturnsFalse() {
        UserDetailsImpl user1 = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl user2 = UserDetailsImpl.builder().id(2L).build();

        assertFalse(user1.equals(user2));
        assertFalse(user2.equals(user1));
    }


}
