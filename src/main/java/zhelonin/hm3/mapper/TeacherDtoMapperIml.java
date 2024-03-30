package zhelonin.hm3.mapper;

import zhelonin.hm3.dto.TeacherIncomingDTO;
import zhelonin.hm3.dto.TeacherOutgoingDTO;
import zhelonin.hm3.dto.TeacherUpdateDTO;
import zhelonin.hm3.entity.Teacher;
import zhelonin.hm3.mapper.specification.TeacherDtoMapper;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TeacherDtoMapperIml implements TeacherDtoMapper {

  public TeacherDtoMapperIml() {
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
