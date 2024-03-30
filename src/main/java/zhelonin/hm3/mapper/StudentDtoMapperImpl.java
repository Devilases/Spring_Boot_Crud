package zhelonin.hm3.mapper;

import zhelonin.hm3.dto.StudentIncomingDTO;
import zhelonin.hm3.dto.StudentOutgoingDTO;
import zhelonin.hm3.dto.StudentUpdateDTO;
import zhelonin.hm3.entity.Student;
import zhelonin.hm3.mapper.specification.StudentDtoMapper;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StudentDtoMapperImpl implements StudentDtoMapper {

  public StudentDtoMapperImpl() {
  }

  @Override
  public Student map(StudentIncomingDTO studentIncomingDTO) {
    return new Student(
        null,
        studentIncomingDTO.getName(),
        null
    );
  }

  @Override
  public StudentOutgoingDTO map(Student student) {
    return new StudentOutgoingDTO(
        student.getId(),
        student.getName()
    );
  }

  @Override
  public Student map(StudentUpdateDTO studentUpdateDTO) {
    return new Student(
        studentUpdateDTO.getId(),
        studentUpdateDTO.getName(),
        null
    );
  }

  @Override
  public List<StudentOutgoingDTO> map(List<Student> studentList) {
    return studentList.stream().map(this::map).toList();
  }

  @Override
  public List<Student> mapUpdateList(List<StudentUpdateDTO> studentUpdateDTOList) {
    return studentUpdateDTOList.stream().map(this::map).toList();
  }
}
