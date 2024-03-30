package zhelonin.hm3.mapper.specification;

import zhelonin.hm3.dto.StudentIncomingDTO;
import zhelonin.hm3.dto.StudentOutgoingDTO;
import zhelonin.hm3.dto.StudentUpdateDTO;
import zhelonin.hm3.entity.Student;
import java.util.List;

public interface StudentDtoMapper {
  Student map(StudentIncomingDTO studentIncomingDTO);

  StudentOutgoingDTO map(Student student);

  Student map(StudentUpdateDTO studentUpdateDTO);

  List<StudentOutgoingDTO> map(List<Student> studentList);

  List<Student> mapUpdateList(List<StudentUpdateDTO> studentUpdateDTOList);

}
