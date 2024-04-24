package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class SessionTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SessionMapper sessionMapper = new SessionMapperImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToEntity() {
        // Given
        SessionDto sessionDto = new SessionDto();
        sessionDto.setDescription("Test Description");
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(Collections.singletonList(1L));

        Teacher teacher = new Teacher();
        teacher.setId(1L);

        User user = new User();
        user.setId(1L);

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(1L)).thenReturn(user);

        // When
        Session session = sessionMapper.toEntity(sessionDto);

        // Then
        assertThat(session).isNotNull();
        assertThat(session.getDescription()).isEqualTo(sessionDto.getDescription());
        assertThat(session.getTeacher()).isEqualTo(teacher);
        assertThat(session.getUsers()).containsExactly(user);
    }

    @Test
    public void testToDto() {
        // Given
        Session session = new Session();
        session.setDescription("Test Description");

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        session.setTeacher(teacher);

        User user = new User();
        user.setId(1L);
        List<User> users = new ArrayList<>();
        users.add(user);
        session.setUsers(users);

        // When
        SessionDto sessionDto = sessionMapper.toDto(session);

        // Then
        assertThat(sessionDto).isNotNull();
        assertThat(sessionDto.getDescription()).isEqualTo(session.getDescription());
        assertThat(sessionDto.getTeacher_id()).isEqualTo(teacher.getId());
        assertThat(sessionDto.getUsers()).containsExactly(user.getId());
    }
}
