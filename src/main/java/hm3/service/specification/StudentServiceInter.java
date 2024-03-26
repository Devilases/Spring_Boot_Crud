package hm3.service.specification;

import hm3.dto.StudentIncomingDTO;
import hm3.dto.StudentOutgoingDTO;
import hm3.dto.StudentUpdateDTO;
import hm3.exception.NotFoundException;
import java.util.List;

public interface StudentServiceInter {
  StudentOutgoingDTO save(StudentIncomingDTO project);

  void update(StudentUpdateDTO project) throws NotFoundException;

  StudentOutgoingDTO findById(Integer studentId) throws NotFoundException;

  List<StudentOutgoingDTO> findAll();

  void delete(Integer studentId) throws NotFoundException;

  void deleteLessonFromStudent(Integer studentId, Integer lessonId) throws NotFoundException;

  void addLessonToStudent(Integer studentId, Integer lessonId) throws NotFoundException;

}
