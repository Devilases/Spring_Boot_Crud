package hm3.mapper;

import hm3.dto.TeacherIncomingDTO;
import hm3.dto.TeacherOutgoingDTO;
import hm3.dto.TeacherUpdateDTO;
import hm3.entity.Teacher;
import hm3.mapper.specification.TeacherDtoMapper;
import java.util.List;

public class TeacherDtoMapperIml implements TeacherDtoMapper {

  private static TeacherDtoMapper instance;

  private TeacherDtoMapperIml() {
  }

  public static synchronized TeacherDtoMapper getInstance() {
    if (instance == null) {
      instance = new TeacherDtoMapperIml();
    }
    return instance;
  }

  @Override
  public Teacher map(TeacherIncomingDTO teacherIncomingDTO) {
    return new Teacher(
        null,
        teacherIncomingDTO.getName()
    );
  }

  @Override
  public Teacher map(TeacherUpdateDTO teacherUpdateDTO) {
    return new Teacher(
        teacherUpdateDTO.getId(),
        teacherUpdateDTO.getName()
    );
  }

  @Override
  public TeacherOutgoingDTO map(Teacher teacher) {
    return new TeacherOutgoingDTO(
        teacher.getId(),
        teacher.getName()
    );
  }

  @Override
  public List<TeacherOutgoingDTO> map(List<Teacher> teacherList) {
    return teacherList.stream().map(this::map).toList();
  }
}
