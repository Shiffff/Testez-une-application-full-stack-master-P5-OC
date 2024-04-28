package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        user = User.builder()
                .email("john.doe@example.com")
                .lastName("Doe")
                .firstName("John")
                .password("password")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        user = userRepository.save(user);
        userDto = userMapper.toDto(user);
    }

    @Test
    @WithMockUser(username = "john.doe@example.com")
    public void testFindUserById_WithValidId() throws Exception {
        Long validId = user.getId();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        UserDto responseUserDto = objectMapper.readValue(content, UserDto.class);
        assertThat(responseUserDto).isNotNull();
        assertThat(responseUserDto.getId()).isEqualTo(validId);
        assertThat(responseUserDto.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(responseUserDto.getFirstName()).isEqualTo(userDto.getFirstName());
        assertThat(responseUserDto.getLastName()).isEqualTo(userDto.getLastName());
        assertThat(responseUserDto.isAdmin()).isEqualTo(userDto.isAdmin());
    }

    @Test
    @WithMockUser(username = "john.doe@example.com")
    public void testFindUserById_WithInvalidId() throws Exception {
        Long invalidId = 999L;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "john.doe@example.com")
    public void testDeleteUserById_WithValidId() throws Exception {

        Long validId = user.getId();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Optional<User> deletedUser = userRepository.findById(validId);
        assertThat(deletedUser).isEmpty();
    }

}
