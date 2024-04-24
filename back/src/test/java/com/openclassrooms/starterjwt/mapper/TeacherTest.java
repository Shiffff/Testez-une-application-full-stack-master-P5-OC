package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class TeacherTest {

    private TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

    @Test
    public void testToEntityList() {
        // Créer une liste d'objets TeacherDto
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

        // Mapper la liste d'objets TeacherDto vers une liste d'objets Teacher
        List<Teacher> teachers = teacherMapper.toEntity(teacherDtos);

        // Vérifier si le mapping est correct pour chaque objet
        assertThat(teachers).isNotEmpty().hasSize(teacherDtos.size());

        for (int i = 0; i < teacherDtos.size(); i++) {
            assertThat(teachers.get(i))
                    .usingRecursiveComparison()
                    .isEqualTo(teacherDtos.get(i));
        }
    }

    @Test
    public void testToDtoList() {
        // Créer une liste d'objets Teacher
        List<Teacher> teachers = new ArrayList<>();
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setFirstName("John");
        teacher1.setLastName("Doe");
        teacher1.setCreatedAt(LocalDateTime.now());
        teacher1.setUpdatedAt(LocalDateTime.now());

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setFirstName("Jane");
        teacher2.setLastName("Smith");
        teacher2.setCreatedAt(LocalDateTime.now());
        teacher2.setUpdatedAt(LocalDateTime.now());

        teachers.add(teacher1);
        teachers.add(teacher2);

        // Mapper la liste d'objets Teacher vers une liste d'objets TeacherDto
        List<TeacherDto> teacherDtos = teacherMapper.toDto(teachers);

        // Vérifier si le mapping est correct pour chaque objet
        assertThat(teacherDtos).isNotEmpty().hasSize(teachers.size());

        for (int i = 0; i < teachers.size(); i++) {
            assertThat(teacherDtos.get(i))
                    .usingRecursiveComparison()
                    .isEqualTo(teachers.get(i));
        }
    }
}
