package zhelonin.hm3.controller;


import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhelonin.hm3.dto.StudentIncomingDTO;
import zhelonin.hm3.dto.StudentOutgoingDTO;
import zhelonin.hm3.dto.StudentUpdateDTO;

import zhelonin.hm3.exception.NotFoundException;
import zhelonin.hm3.service.specification.StudentServiceInter;



@RestController
@Slf4j
//@WebServlet(urlPatterns = {"/teacher/*"})
@RequestMapping("/stud/*")
public class StudentRestController {

  private StudentServiceInter studentService;

  public StudentRestController(StudentServiceInter studentService) {
    this.studentService = studentService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<StudentOutgoingDTO>> getAllStudents(){
    List<StudentOutgoingDTO> all = studentService.findAll();
    return ResponseEntity.ok(all);
  }

  @GetMapping("/{id}")
  public ResponseEntity<StudentOutgoingDTO> getStudentById(@PathVariable Integer id)
      throws NotFoundException {
      StudentOutgoingDTO studentById = studentService.findById(id);
      return ResponseEntity.ok(studentById);
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<Void> deleteStudentById(@PathVariable Integer id) throws NotFoundException {
      studentService.delete(id);
      return ResponseEntity.noContent().build();

  }
  @PostMapping
  public ResponseEntity<StudentOutgoingDTO> saveStudent(@RequestBody StudentIncomingDTO studentIncomingDTO){
    StudentOutgoingDTO studentOutgoingDTO = studentService.save(studentIncomingDTO);
    return ResponseEntity.ok(studentOutgoingDTO);
  }

  @PutMapping
  public ResponseEntity<Void> updateStudent(@RequestBody StudentUpdateDTO studentUpdateDTO)
      throws NotFoundException {
      studentService.update(studentUpdateDTO);
      return ResponseEntity.accepted().build();
  }

  @PutMapping("/{studId}/addLesson/{lessId}")
  public ResponseEntity<Void> addLessonToStudent(@PathVariable Integer studId,@PathVariable Integer lessId)
      throws NotFoundException {
      log.info("addLesson start");
      studentService.addLessonToStudent(studId, lessId);
      log.info("addLesson end");
      return ResponseEntity.accepted().build();

  }

  @PutMapping("/{studId}/deleteLesson/{lessId}")
  public ResponseEntity<Void> deleteLessonFromStudent(@PathVariable Integer studId,@PathVariable Integer lessId)
      throws NotFoundException {
      studentService.deleteLessonFromStudent(studId, lessId);
      return ResponseEntity.accepted().build();
  }
}
