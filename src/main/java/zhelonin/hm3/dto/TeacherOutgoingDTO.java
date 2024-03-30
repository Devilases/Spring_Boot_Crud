package zhelonin.hm3.dto;

public class TeacherOutgoingDTO {

  private Integer id;
  private String name;

  public TeacherOutgoingDTO() {
  }

  public TeacherOutgoingDTO(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

}
