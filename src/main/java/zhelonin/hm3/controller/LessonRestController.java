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
import zhelonin.hm3.dto.LessonIncomingDTO;
import zhelonin.hm3.dto.LessonOutgoingDTO;
import zhelonin.hm3.dto.LessonUpdateDTO;
import zhelonin.hm3.exception.NotFoundException;
import zhelonin.hm3.service.specification.LessonService;

@RestController
@Slf4j
//@WebServlet(urlPatterns = {"/teacher/*"})
@RequestMapping("/less/*")
public class LessonRestController {

  private final LessonService lessonService;

  public LessonRestController(LessonService lessonService) {
    this.lessonService = lessonService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<LessonOutgoingDTO>> getAllLessons(){
    List<LessonOutgoingDTO> all = lessonService.findAll();
    return ResponseEntity.ok(all);
  }

  @GetMapping("/{id}")
  public ResponseEntity<LessonOutgoingDTO> getLessonById(@PathVariable Integer id)
      throws NotFoundException {
      LessonOutgoingDTO lessonById = lessonService.findById(id);
      return ResponseEntity.ok(lessonById);
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<Void> deleteLessonById(@PathVariable Integer id) throws NotFoundException {
      lessonService.delete(id);
      return ResponseEntity.noContent().build();
  }
  @PostMapping
  public ResponseEntity<LessonOutgoingDTO> saveLesson(@RequestBody LessonIncomingDTO lessonIncomingDTO){
    LessonOutgoingDTO lessonOutgoingDTO = lessonService.save(lessonIncomingDTO);
    return ResponseEntity.ok(lessonOutgoingDTO);
  }

  @PutMapping
  public ResponseEntity<Void> updateLesson(@RequestBody LessonUpdateDTO lessonUpdateDTO)
      throws NotFoundException {
      lessonService.update(lessonUpdateDTO);
      return ResponseEntity.accepted().build();
  }
}
