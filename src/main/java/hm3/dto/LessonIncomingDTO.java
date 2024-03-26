package hm3.dto;

import hm3.entity.Teacher;

public class LessonIncomingDTO {

  private String name;
  private Teacher teacher;

  public LessonIncomingDTO() {
  }

  public LessonIncomingDTO(String name, Teacher teacher) {
    this.name = name;
    this.teacher = teacher;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }
}
