package hm3.dto;


import java.util.List;

public class StudentOutgoingDTO {
  private Integer id;
  private String name;
  private List<LessonSmallOutgoingDTO> lessonSmallOutgoingDTO;

  public StudentOutgoingDTO() {
  }

  public StudentOutgoingDTO(Integer id, String name,
      List<LessonSmallOutgoingDTO> lessonSmallOutgoingDTO) {
    this.id = id;
    this.name = name;
    this.lessonSmallOutgoingDTO = lessonSmallOutgoingDTO;
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

  public List<LessonSmallOutgoingDTO> getLessonSmallOutgoingDTO() {
    return lessonSmallOutgoingDTO;
  }

  public void setLessonSmallOutgoingDTO(
      List<LessonSmallOutgoingDTO> lessonSmallOutgoingDTO) {
    this.lessonSmallOutgoingDTO = lessonSmallOutgoingDTO;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("StudentOutgoingDTO{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", lessonSmallOutgoingDTO=").append(lessonSmallOutgoingDTO);
    sb.append('}');
    return sb.toString();
  }
}
