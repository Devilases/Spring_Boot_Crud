package zhelonin.hm3.dto;

import java.util.List;

public class LessonUpdateDTO {

  private Integer id;
  private String name;

  private TeacherUpdateDTO teacherUpdateDTO;

  List<StudentUpdateDTO> studentUpdateDTOS;

  public LessonUpdateDTO() {
  }

  public LessonUpdateDTO(Integer id, String name, TeacherUpdateDTO teacherUpdateDTO,
      List<StudentUpdateDTO> studentUpdateDTOS) {
    this.id = id;
    this.name = name;
    this.teacherUpdateDTO = teacherUpdateDTO;
    this.studentUpdateDTOS = studentUpdateDTOS;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TeacherUpdateDTO getTeacherUpdateDTO() {
    return teacherUpdateDTO;
  }

  public void setTeacherUpdateDTO(TeacherUpdateDTO teacherUpdateDTO) {
    this.teacherUpdateDTO = teacherUpdateDTO;
  }

  public List<StudentUpdateDTO> getStudentUpdateDTOS() {
    return studentUpdateDTOS;
  }

  public void setStudentUpdateDTOS(List<StudentUpdateDTO> studentUpdateDTOS) {
    this.studentUpdateDTOS = studentUpdateDTOS;
  }
}
