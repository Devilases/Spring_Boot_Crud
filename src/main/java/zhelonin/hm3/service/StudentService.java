package zhelonin.hm3.service;

import lombok.extern.slf4j.Slf4j;
import zhelonin.hm3.dto.StudentIncomingDTO;
import zhelonin.hm3.dto.StudentOutgoingDTO;
import zhelonin.hm3.dto.StudentUpdateDTO;
import zhelonin.hm3.entity.Lesson;
import zhelonin.hm3.entity.Student;
import zhelonin.hm3.exception.NotFoundException;
import zhelonin.hm3.mapper.specification.StudentDtoMapper;
import zhelonin.hm3.repo.boot.LessonRepository;
import zhelonin.hm3.repo.boot.StudentRepository;
import zhelonin.hm3.repo.specification.LessonRepo;
import zhelonin.hm3.repo.specification.StudentRepo;
import zhelonin.hm3.service.specification.StudentServiceInter;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService implements StudentServiceInter {

  private StudentRepository studentRepo;
  private StudentDtoMapper studentDtoMapper;
  private LessonRepository lessonRepo;

  public StudentService(StudentRepository studentRepo, StudentDtoMapper studentDtoMapper,
      LessonRepository lessonRepo) {
    this.studentRepo = studentRepo;
    this.studentDtoMapper = studentDtoMapper;
    this.lessonRepo = lessonRepo;
  }

  private void checkStudentExist(Integer studentId) throws NotFoundException {
    if (!studentRepo.existsById(studentId)) {
      throw new NotFoundException("Student not found.");
    }
  }

  @Override
  public StudentOutgoingDTO save(StudentIncomingDTO studentIncomingDTO) {
    Student student = studentDtoMapper.map(studentIncomingDTO);
    student = studentRepo.save(student);
    return studentDtoMapper.map(student);
  }

  @Override
  public void update(StudentUpdateDTO studentUpdateDTO) throws NotFoundException {
    checkStudentExist(studentUpdateDTO.getId());
    studentRepo.saveAndFlush(studentDtoMapper.map(studentUpdateDTO));
  }

  @Override
  public StudentOutgoingDTO findById(Integer studentId) throws NotFoundException {
    checkStudentExist(studentId);
    Student student = studentRepo.findById(studentId).orElseThrow(()-> new NotFoundException("Student not found."));
    return studentDtoMapper.map(student);
  }

  @Override
  public List<StudentOutgoingDTO> findAll() {
    List<Student> studentList = studentRepo.findAll();
    return studentDtoMapper.map(studentList);
  }

  @Override
  public void delete(Integer studentId) throws NotFoundException {
    checkStudentExist(studentId);
    studentRepo.deleteById(studentId);
  }

  @Override
  public void deleteLessonFromStudent(Integer studentId, Integer lessonId) throws NotFoundException {
    checkStudentExist(studentId);
    if(lessonRepo.existsById(lessonId)){
      log.info("deleteLessonFromStudent start");
      Student student = studentRepo.findById(studentId).orElseThrow(()-> new NotFoundException("Student not found."));
      Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(()-> new NotFoundException("Student not found."));
      student.deleteLesson(lesson);

      studentRepo.saveAndFlush(student);
    }else {
      throw new NotFoundException("Lesson not found");
    }
  }

  @Override
  public void addLessonToStudent(Integer studentId, Integer lessonId) throws NotFoundException {
    checkStudentExist(studentId);
    if(lessonRepo.existsById(lessonId)){
      log.info("addLessonToStudent start");
      Student student = studentRepo.findById(studentId).orElseThrow(()-> new NotFoundException("Student not found."));
      Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(()-> new NotFoundException("Student not found."));
      lesson.addStudent(student);

      lessonRepo.saveAndFlush(lesson);
    }else {
      throw new NotFoundException("Lesson not found");
    }
  }
}
