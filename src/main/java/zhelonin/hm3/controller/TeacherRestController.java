package zhelonin.hm3.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhelonin.hm3.dto.TeacherIncomingDTO;
import zhelonin.hm3.dto.TeacherOutgoingDTO;
import zhelonin.hm3.dto.TeacherUpdateDTO;
import zhelonin.hm3.exception.NotFoundException;
import zhelonin.hm3.service.specification.TeacherServiceInter;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@Slf4j
@RequestMapping("/teacher/*")

public class TeacherRestController {

  private TeacherServiceInter teacherService;

  @Autowired
  public TeacherRestController(TeacherServiceInter teacherService) {
    this.teacherService = teacherService;

  }

  @GetMapping("/all")
  public ResponseEntity<List<TeacherOutgoingDTO>> getAllTeachers(){
    List<TeacherOutgoingDTO> all = teacherService.findAll();
    log.info("getAllTeachers Servlet return value");
    return ResponseEntity.ok(all);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TeacherOutgoingDTO> getTeacherById(@PathVariable Integer id)
      throws NotFoundException {

      TeacherOutgoingDTO teacherById = teacherService.findById(id);
      return ResponseEntity.ok(teacherById);
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<Void> deleteTeacherById(@PathVariable Integer id) throws NotFoundException {
      teacherService.delete(id);
      return ResponseEntity.noContent().build();
  }
  @PostMapping
  public ResponseEntity<TeacherOutgoingDTO> saveTeacher(@RequestBody TeacherIncomingDTO teacherIncomingDTO){
    TeacherOutgoingDTO teacherOutgoingDTO = teacherService.save(teacherIncomingDTO);
    return ResponseEntity.ok(teacherOutgoingDTO);
  }

  @PutMapping
  public ResponseEntity<Void> updateTeacher(@RequestBody TeacherUpdateDTO teacherUpdateDTO)
      throws NotFoundException {
      teacherService.update(teacherUpdateDTO);
      return ResponseEntity.accepted().build();
  }
}
