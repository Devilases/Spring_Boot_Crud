package hm3;

import hm3.dto.LessonIncomingDTO;
import hm3.dto.StudentIncomingDTO;
import hm3.dto.TeacherIncomingDTO;
import hm3.entity.Teacher;
import hm3.exception.NotFoundException;
import hm3.repo.LessonRepository;
import hm3.repo.StudentRepository;
import hm3.repo.TeacherRepository;
import hm3.repo.specification.LessonRepo;
import hm3.repo.specification.StudentRepo;
import hm3.repo.specification.TeacherRepo;

import hm3.service.LessonService;
import hm3.service.StudentService;

import hm3.service.TeacherService;
import hm3.service.specification.LessonServiceInter;
import hm3.service.specification.StudentServiceInter;
import hm3.service.specification.TeacherServiceInter;

public class Main {

  public static void main(String[] args) throws NotFoundException {
    TeacherRepo teacherRepo = TeacherRepository.getInstance();
    StudentServiceInter studentServiceInter = StudentService.getInstance();
    StudentRepo studentRepo = StudentRepository.getInstance();
    System.out.println(studentRepo.findById(1));
    TeacherServiceInter teacherServiceInter = TeacherService.getInstance();
    teacherServiceInter.save(new TeacherIncomingDTO("fhwwl"));
    System.out.println(studentServiceInter.findById(1));
    studentServiceInter.save(new StudentIncomingDTO("dsada"));

//    LessonRepo lessonRepo = LessonRepository.getInstance();
//    System.out.println(lessonRepo.findById(1));


//    LessonServiceInter lessonServiceInter = LessonService.getInstance();
//    System.out.println(lessonServiceInter.findById(2));
//    System.out.println(lessonServiceInter.findAll());
//
//    lessonServiceInter.save(new LessonIncomingDTO("ddwd", new Teacher(4, "ddd")));


  }
}
