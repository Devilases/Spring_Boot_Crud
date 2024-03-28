package hm3;

import hm3.dto.LessonIncomingDTO;
import hm3.dto.StudentIncomingDTO;
import hm3.dto.StudentUpdateDTO;
import hm3.entity.Teacher;
import hm3.exception.NotFoundException;
import hm3.service.LessonService;
import hm3.service.StudentService;
import hm3.service.specification.LessonServiceInter;
import hm3.service.specification.StudentServiceInter;

public class Main {

  public static void main(String[] args) throws NotFoundException {
//    TeacherIncomingDTO teacher = new TeacherIncomingDTO("Serg");
//    TeacherUpdateDTO teacherUpdateDTO = new TeacherUpdateDTO(2, "Herha");
//
//    TeacherServiceInter teacherService = TeacherService.getInstance();
//    System.out.println(teacherService.findById(2));
//
//    System.out.println(teacherService.findAll());
//    teacherService.update(teacherUpdateDTO);


    Teacher teacher = new Teacher(2, "Herha");
    LessonIncomingDTO lessonIncomingDTO = new LessonIncomingDTO("Herass", teacher);
    LessonServiceInter lessonServiceInter = LessonService.getInstance();
    //LessonOutgoingDTO save = lessonServiceInter.save(lessonIncomingDTO);
    //System.out.println(save);
    //System.out.println(lessonServiceInter.findAll());
    //lessonServiceInter.delete(5);
 //   System.out.println(lessonServiceInter.findById(7));



    StudentIncomingDTO studentIncomingDTO = new StudentIncomingDTO("Gehrs");
    StudentUpdateDTO studentUpdateDTO = new StudentUpdateDTO(1,"dddd");
    StudentServiceInter studentServiceInter = StudentService.getInstance();
    //studentServiceInter.save(studentIncomingDTO);
    //System.out.println(studentServiceInter.findById(1));
    System.out.println(studentServiceInter.findAll());
    studentServiceInter.addLessonToStudent(1, 14);
    //studentServiceInter.deleteLessonFromStudent(1, 7);
    //studentServiceInter.update(studentUpdateDTO);

  }
}
