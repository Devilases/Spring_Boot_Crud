package zhelonin.hm3.service.specification;

import zhelonin.hm3.dto.StudentIncomingDTO;
import zhelonin.hm3.dto.StudentOutgoingDTO;
import zhelonin.hm3.dto.StudentUpdateDTO;
import zhelonin.hm3.exception.NotFoundException;
import java.util.List;

public interface StudentService {
  StudentOutgoingDTO save(StudentIncomingDTO project);

  void update(StudentUpdateDTO project) throws NotFoundException;

  StudentOutgoingDTO findById(Integer studentId) throws NotFoundException;

  List<StudentOutgoingDTO> findAll();

  void delete(Integer studentId) throws NotFoundException;

  void deleteLessonFromStudent(Integer studentId, Integer lessonId) throws NotFoundException;

  void addLessonToStudent(Integer studentId, Integer lessonId) throws NotFoundException;

}
