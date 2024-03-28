package hm3.service;

import hm3.dto.StudentIncomingDTO;
import hm3.dto.StudentOutgoingDTO;
import hm3.dto.StudentUpdateDTO;
import hm3.entity.Lesson;
import hm3.entity.Student;
import hm3.exception.NotFoundException;
import hm3.mapper.StudentDtoMapperImpl;
import hm3.mapper.specification.StudentDtoMapper;
import hm3.repo.hiber.LessonRepoHibernateImpl;
import hm3.repo.hiber.StudentRepoHibernateImpl;
import hm3.repo.specification.LessonRepo;
import hm3.repo.specification.StudentRepo;
import hm3.service.specification.StudentServiceInter;
import java.util.List;

public class StudentService implements StudentServiceInter {

  private StudentRepo studentRepo = StudentRepoHibernateImpl.getInstance();
  private StudentDtoMapper studentDtoMapper = StudentDtoMapperImpl.getInstance();
  private LessonRepo lessonRepo = LessonRepoHibernateImpl.getInstance();
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
      Student student = studentRepo.findById(studentId).orElseThrow(()-> new NotFoundException("Student not found."));
      Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(()-> new NotFoundException("Student not found."));
      student.deleteLesson(lesson);

      studentRepo.update(student);
    }else {
      throw new NotFoundException("Lesson not found");
    }
  }

  @Override
  public void addLessonToStudent(Integer studentId, Integer lessonId) throws NotFoundException {
    checkStudentExist(studentId);
    if(lessonRepo.exitsById(lessonId)){
      Student student = studentRepo.findById(studentId).orElseThrow(()-> new NotFoundException("Student not found."));
      Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(()-> new NotFoundException("Student not found."));
      lesson.addStudent(student);

      lessonRepo.update(lesson);
    }else {
      throw new NotFoundException("Lesson not found");
    }
  }
}
