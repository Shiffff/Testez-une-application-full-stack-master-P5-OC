package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testToEntityList() {
        List<UserDto> userDtos = new ArrayList<>();
        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setEmail("john.doe@example.com");
        userDto1.setFirstName("John");
        userDto1.setLastName("Doe");
        userDto1.setPassword("password");
        userDto1.setAdmin(true);
        userDto1.setCreatedAt(LocalDateTime.now());
        userDto1.setUpdatedAt(LocalDateTime.now());

        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setEmail("jane.smith@example.com");
        userDto2.setFirstName("Jane");
        userDto2.setLastName("Smith");
        userDto2.setPassword("password");
        userDto2.setAdmin(false);
        userDto2.setCreatedAt(LocalDateTime.now());
        userDto2.setUpdatedAt(LocalDateTime.now());

        userDtos.add(userDto1);
        userDtos.add(userDto2);

        List<User> users = userMapper.toEntity(userDtos);
        assertThat(users).isNotEmpty().hasSize(userDtos.size());

        for (int i = 0; i < userDtos.size(); i++) {
            UserDto userDto = userDtos.get(i);
            User user = users.get(i);

            assertThat(user.getId()).isEqualTo(userDto.getId());
            assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
            assertThat(user.getFirstName()).isEqualTo(userDto.getFirstName());
            assertThat(user.getLastName()).isEqualTo(userDto.getLastName());
            assertThat(user.isAdmin()).isEqualTo(userDto.isAdmin());
            assertThat(user.getCreatedAt()).isEqualTo(userDto.getCreatedAt());
            assertThat(user.getUpdatedAt()).isEqualTo(userDto.getUpdatedAt());
        }
    }

    @Test
    public void testToDtoList() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("john.doe@example.com");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setPassword("password");
        user1.setAdmin(true);
        user1.setCreatedAt(LocalDateTime.now());
        user1.setUpdatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("jane.smith@example.com");
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setPassword("password");
        user2.setAdmin(false);
        user2.setCreatedAt(LocalDateTime.now());
        user2.setUpdatedAt(LocalDateTime.now());

        users.add(user1);
        users.add(user2);

        List<UserDto> userDtos = userMapper.toDto(users);

        assertThat(userDtos).isNotEmpty().hasSize(users.size());

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            UserDto userDto = userDtos.get(i);

            assertThat(userDto.getId()).isEqualTo(user.getId());
            assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
            assertThat(userDto.getFirstName()).isEqualTo(user.getFirstName());
            assertThat(userDto.getLastName()).isEqualTo(user.getLastName());
            assertThat(userDto.isAdmin()).isEqualTo(user.isAdmin());
            assertThat(userDto.getCreatedAt()).isEqualTo(user.getCreatedAt());
            assertThat(userDto.getUpdatedAt()).isEqualTo(user.getUpdatedAt());
        }
    }
}
