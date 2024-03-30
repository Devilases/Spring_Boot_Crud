package zhelonin.hm3.mapper.specification;

import zhelonin.hm3.dto.TeacherIncomingDTO;
import zhelonin.hm3.dto.TeacherOutgoingDTO;
import zhelonin.hm3.dto.TeacherUpdateDTO;
import zhelonin.hm3.entity.Teacher;
import java.util.List;

public interface TeacherDtoMapper {
  Teacher map(TeacherIncomingDTO teacherIncomingDTO);

  Teacher map(TeacherUpdateDTO teacherUpdateDTO);

  TeacherOutgoingDTO map(Teacher teacher);

  List<TeacherOutgoingDTO> map(List<Teacher> teacherList);

}
