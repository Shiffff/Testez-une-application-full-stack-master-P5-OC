package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class SessionServiceTest {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void cleanup() {
        sessionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testCreateSession() {

        Session sessionToCreate = new Session();
        sessionToCreate.setName("Test Session");
        sessionToCreate.setDate(new Date());
        sessionToCreate.setDescription("Test Description");

        Session createdSession = sessionService.create(sessionToCreate);

        assertNotNull(createdSession.getId());
        assertEquals(sessionToCreate.getName(), createdSession.getName());
        assertEquals(1, sessionRepository.count());
    }

    @Test
    void testDeleteSession() {

        Session sessionToCreate = new Session();
        sessionToCreate.setName("Test Session");
        sessionToCreate.setDate(new Date());
        sessionToCreate.setDescription("Test Description");
        Session createdSession = sessionService.create(sessionToCreate);

        sessionService.delete(createdSession.getId());


        assertNull(sessionService.getById(createdSession.getId()));
    }

    @Test
    void testParticipateInSession() {

        Session session = new Session();
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Test Description");
        session = sessionService.create(session);

        User user = new User();
        user.setEmail("test_user@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAdmin(false);
        user = userRepository.save(user);

        Long sessionId = session.getId();
        Long userId = user.getId();


        sessionService.participate(sessionId, userId);


        Session updatedSession = sessionService.getById(sessionId);
        assertNotNull(updatedSession);
        assertEquals(1, updatedSession.getUsers().size());
        assertTrue(updatedSession.getUsers().contains(user));
    }

    @Test
    void testNoLongerParticipateInSession() {
        // Given
        Session session = new Session();
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Test Description");
        session = sessionService.create(session);

        User user = new User();
        user.setEmail("test_user@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAdmin(false);
        user = userRepository.save(user);

        sessionService.participate(session.getId(), user.getId());

        Long sessionId = session.getId();
        Long userId = user.getId();


        sessionService.noLongerParticipate(sessionId, userId);


        Session updatedSession = sessionService.getById(sessionId);
        assertNotNull(updatedSession);
        assertTrue(updatedSession.getUsers().isEmpty());
    }


}
