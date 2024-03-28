package hm3.mapper;

import hm3.dto.StudentIncomingDTO;
import hm3.dto.StudentOutgoingDTO;
import hm3.dto.StudentUpdateDTO;
import hm3.entity.Student;
import hm3.mapper.specification.StudentDtoMapper;
import java.util.List;

public class StudentDtoMapperImpl implements StudentDtoMapper {

  private static StudentDtoMapper instance;

  private StudentDtoMapperImpl() {
  }

  public static synchronized StudentDtoMapper getInstance() {
    if (instance == null) {
      instance = new StudentDtoMapperImpl();
    }
    return instance;
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
