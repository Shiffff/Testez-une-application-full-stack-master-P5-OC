package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class TeacherMapperTest {

    private TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

    @Test
    public void testToEntityList() {
        List<TeacherDto> teacherDtos = new ArrayList<>();
        TeacherDto teacherDto1 = new TeacherDto();
        teacherDto1.setId(1L);
        teacherDto1.setFirstName("John");
        teacherDto1.setLastName("Doe");
        teacherDto1.setCreatedAt(LocalDateTime.now());
        teacherDto1.setUpdatedAt(LocalDateTime.now());

        TeacherDto teacherDto2 = new TeacherDto();
        teacherDto2.setId(2L);
        teacherDto2.setFirstName("Jane");
        teacherDto2.setLastName("Smith");
        teacherDto2.setCreatedAt(LocalDateTime.now());
        teacherDto2.setUpdatedAt(LocalDateTime.now());

        teacherDtos.add(teacherDto1);
        teacherDtos.add(teacherDto2);

        List<Teacher> teachers = teacherMapper.toEntity(teacherDtos);

        assertThat(teachers).isNotEmpty().hasSize(teacherDtos.size());

        for (int i = 0; i < teacherDtos.size(); i++) {
            assertThat(teachers.get(i))
                    .usingRecursiveComparison()
                    .isEqualTo(teacherDtos.get(i));
        }
    }

}
