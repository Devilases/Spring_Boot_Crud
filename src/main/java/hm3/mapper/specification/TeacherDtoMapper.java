package hm3.mapper.specification;

import hm3.dto.TeacherIncomingDTO;
import hm3.dto.TeacherOutgoingDTO;
import hm3.dto.TeacherUpdateDTO;
import hm3.entity.Teacher;
import java.util.List;

public interface TeacherDtoMapper {
  Teacher map(TeacherIncomingDTO teacherIncomingDTO);

  Teacher map(TeacherUpdateDTO teacherUpdateDTO);

  TeacherOutgoingDTO map(Teacher teacher);

  List<TeacherOutgoingDTO> map(List<Teacher> teacherList);

}
