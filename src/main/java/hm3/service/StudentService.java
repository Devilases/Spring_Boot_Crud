package hm3.service;

import hm3.dto.StudentIncomingDTO;
import hm3.dto.StudentOutgoingDTO;
import hm3.dto.StudentUpdateDTO;
import hm3.entity.LessonToStudentReservation;
import hm3.entity.Student;
import hm3.exception.NotFoundException;
import hm3.mapper.StudentDtoMapperImpl;
import hm3.mapper.specification.StudentDtoMapper;
import hm3.repo.LessonRepository;
import hm3.repo.LessonToStudentRepository;
import hm3.repo.StudentRepository;
import hm3.repo.specification.LessonRepo;
import hm3.repo.specification.LessonToStudentRepo;
import hm3.repo.specification.StudentRepo;
import hm3.service.specification.StudentServiceInter;
import java.util.List;

public class StudentService implements StudentServiceInter {

  private StudentRepo studentRepo = StudentRepository.getInstance();
  private StudentDtoMapper studentDtoMapper = StudentDtoMapperImpl.getInstance();
  private LessonRepo lessonRepo = LessonRepository.getInstance();
  private LessonToStudentRepo lessonToStudentRepo = LessonToStudentRepository.getInstance();
  private static StudentServiceInter instance;

  private StudentService() {
  }

  public static synchronized StudentServiceInter getInstance() {
    if (instance == null) {
      instance = new StudentService();
    }
    return instance;
  }



  private void checkStudentExist(Integer studentId) throws NotFoundException {
    if (!studentRepo.exitsById(studentId)) {
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
    studentRepo.update(studentDtoMapper.map(studentUpdateDTO));
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
    if(lessonRepo.exitsById(lessonId)){
      LessonToStudentReservation lessonToStudentReservation = lessonToStudentRepo.findByStudentIdAndLessonId(studentId, lessonId)
          .orElseThrow(()-> new NotFoundException("Link not found"));
      lessonToStudentRepo.deleteById(lessonToStudentReservation.getId());
    }else {
      throw new NotFoundException("Lesson not found");
    }
  }

  @Override
  public void addLessonToStudent(Integer studentId, Integer lessonId) throws NotFoundException {
    checkStudentExist(studentId);

    if(lessonRepo.exitsById(lessonId)){
      LessonToStudentReservation lessonToStudentReservation = new LessonToStudentReservation(
          null,
          lessonId,
          studentId
      );
      lessonToStudentRepo.save(lessonToStudentReservation);
    }else {
      throw new NotFoundException("Lesson not found");
    }
  }
}
