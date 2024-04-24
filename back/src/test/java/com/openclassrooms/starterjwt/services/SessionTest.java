package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SessionTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    private SessionService sessionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sessionService = new SessionService(sessionRepository, userRepository);
    }

    @Test
    public void testCreateSession() {
        // Given
        Session session = new Session();
        session.setId(1L);

        // When
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        // Then
        Session createdSession = sessionService.create(session);
        assertThat(createdSession.getId()).isEqualTo(1L);
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testDeleteSession() {
        // Given
        Long sessionId = 1L;

        // When
        sessionService.delete(sessionId);

        // Then
        verify(sessionRepository, times(1)).deleteById(sessionId);
    }

    @Test
    public void testFindAllSessions() {
        // Given
        List<Session> sessions = new ArrayList<>();
        sessions.add(new Session());
        sessions.add(new Session());

        // When
        when(sessionRepository.findAll()).thenReturn(sessions);

        // Then
        List<Session> foundSessions = sessionService.findAll();
        assertThat(foundSessions.size()).isEqualTo(2);
        verify(sessionRepository, times(1)).findAll();
    }

    @Test
    public void testGetSessionById() {
        // Given
        Long sessionId = 1L;
        Session session = new Session();
        session.setId(sessionId);

        // When
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // Then
        Session foundSession = sessionService.getById(sessionId);
        assertThat(foundSession).isEqualTo(session);
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void testNoLongerParticipate_SessionNotFound() {
        // Given
        Long sessionId = 1L;
        Long userId = 1L;

        // When
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> sessionService.noLongerParticipate(sessionId, userId));
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void testNoLongerParticipate_UserNotParticipating() {
        // Given
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        session.setId(sessionId);
        session.setUsers(new ArrayList<>()); // Initialize users list

        // When
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // Then
        assertThrows(BadRequestException.class, () -> sessionService.noLongerParticipate(sessionId, userId));
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void testNoLongerParticipate_Success() {
        // Given
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        session.setId(sessionId);
        User user = new User();
        user.setId(userId);
        List<User> users = new ArrayList<>();
        users.add(user);
        session.setUsers(users);

        // When
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // Then
        sessionService.noLongerParticipate(sessionId, userId);
        verify(sessionRepository, times(1)).findById(sessionId);
        verify(sessionRepository, times(1)).save(session);
    }
    @Test
    public void testParticipate_SessionNotFound() {
        // Given
        Long sessionId = 1L;
        Long userId = 1L;

        // When
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void testParticipate_UserAlreadyParticipating() {
        // Given
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        session.setId(sessionId);
        User user = new User();
        user.setId(userId);
        List<User> users = new ArrayList<>();
        users.add(user);
        session.setUsers(users);

        // When
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Then
        assertThrows(BadRequestException.class, () -> sessionService.participate(sessionId, userId));
        verify(sessionRepository, times(1)).findById(sessionId);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testParticipate_Success() {
        // Given
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        session.setId(sessionId);
        User user = new User();
        user.setId(userId);
        List<User> users = new ArrayList<>(); // Initialize the users list
        session.setUsers(users); // Set the initialized users list in the session

        // When
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Then
        sessionService.participate(sessionId, userId);
        verify(sessionRepository, times(1)).findById(sessionId);
        verify(userRepository, times(1)).findById(userId);
        verify(sessionRepository, times(1)).save(session);
    }
    @Test
    public void testUpdateSession() {
        // Given
        Long sessionId = 1L;
        Session session = new Session();
        session.setId(sessionId);

        // When
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        // Then
        Session updatedSession = sessionService.update(sessionId, session);
        assertThat(updatedSession.getId()).isEqualTo(sessionId);
        verify(sessionRepository, times(1)).save(session);
    }


}
