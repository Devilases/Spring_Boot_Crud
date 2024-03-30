package zhelonin.hm3.dto;

import java.util.List;

public class LessonOutgoingDTO {

  private Integer id;
  private String name;
  private TeacherOutgoingDTO teacher;
  List<StudentOutgoingDTO> studentOutgoingDTOList;

  public LessonOutgoingDTO() {
  }

  public LessonOutgoingDTO(Integer id, String name, TeacherOutgoingDTO teacher,
      List<StudentOutgoingDTO> studentOutgoingDTOList) {
    this.id = id;
    this.name = name;
    this.teacher = teacher;
    this.studentOutgoingDTOList = studentOutgoingDTOList;
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

  public TeacherOutgoingDTO getTeacher() {
    return teacher;
  }

  public void setTeacher(TeacherOutgoingDTO teacher) {
    this.teacher = teacher;
  }

  public List<StudentOutgoingDTO> getStudentOutgoingDTOList() {
    return studentOutgoingDTOList;
  }

  public void setStudentOutgoingDTOList(List<StudentOutgoingDTO> studentOutgoingDTOList) {
    this.studentOutgoingDTOList = studentOutgoingDTOList;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("LessonOutgoingDTO{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", teacher=").append(teacher);
    sb.append(", studentOutgoingDTOList=").append(studentOutgoingDTOList);
    sb.append('}');
    return sb.toString();
  }
}
