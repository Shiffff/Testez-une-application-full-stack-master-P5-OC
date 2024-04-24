package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SessionTest {

    private SessionController sessionController;
    private SessionService sessionService;
    private SessionMapper sessionMapper;

    @BeforeEach
    void setUp() {
        sessionService = mock(SessionService.class);
        sessionMapper = mock(SessionMapper.class);
        sessionController = new SessionController(sessionService, sessionMapper);
    }

    @Test
    void findById_SessionExists_ReturnsSessionDto() {
        // Given
        Long id = 1L;
        Session session = createSession();
        SessionDto sessionDto = createSessionDto();

        when(sessionService.getById(id)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        // When
        ResponseEntity<?> response = sessionController.findById(String.valueOf(id));

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    void findById_SessionDoesNotExist_ReturnsNotFound() {
        // Given
        Long id = 1L;
        when(sessionService.getById(id)).thenReturn(null);

        // When
        ResponseEntity<?> response = sessionController.findById(String.valueOf(id));

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findById_InvalidId_ReturnsBadRequest() {
        // When
        ResponseEntity<?> response = sessionController.findById("abc");

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void findAll_ReturnsListOfSessionDto() {
        // Given
        List<Session> sessions = new ArrayList<>();
        sessions.add(createSession());
        sessions.add(createSession());

        List<SessionDto> sessionDtos = new ArrayList<>();
        sessionDtos.add(createSessionDto());
        sessionDtos.add(createSessionDto());

        when(sessionService.findAll()).thenReturn(sessions);
        when(sessionMapper.toDto(sessions)).thenReturn(sessionDtos);

        // When
        ResponseEntity<?> response = sessionController.findAll();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDtos, response.getBody());
    }

    @Test
    void create_ReturnsSessionDto() {
        // Given
        Session session = createSession();
        SessionDto sessionDto = createSessionDto();

        when(sessionService.create(session)).thenReturn(session);
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        // When
        ResponseEntity<?> response = sessionController.create(sessionDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    void update_ReturnsUpdatedSessionDto() {
        // Given
        Long id = 1L;
        Session session = createSession();
        SessionDto sessionDto = createSessionDto();

        when(sessionService.update(id, session)).thenReturn(session);
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        // When
        ResponseEntity<?> response = sessionController.update(String.valueOf(id), sessionDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    void save_ReturnsOk() {
        // Given
        Long id = 1L;
        Session session = createSession();

        when(sessionService.getById(id)).thenReturn(session);

        // When
        ResponseEntity<?> response = sessionController.save(String.valueOf(id));

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void participate_ReturnsOk() {
        // Given
        Long id = 1L;
        Long userId = 1L;

        // When
        ResponseEntity<?> response = sessionController.participate(String.valueOf(id), String.valueOf(userId));

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void noLongerParticipate_ReturnsOk() {
        // Given
        Long id = 1L;
        Long userId = 1L;

        // When
        ResponseEntity<?> response = sessionController.noLongerParticipate(String.valueOf(id), String.valueOf(userId));

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private Session createSession() {
        return new Session(
                1L,
                "Session Name",
                new Date(),
                "Session Description",
                null,
                new ArrayList<>(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    private SessionDto createSessionDto() {
        return new SessionDto(
                1L,
                "Session Name",
                new Date(),
                1L,
                "Session Description",
                new ArrayList<>(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
