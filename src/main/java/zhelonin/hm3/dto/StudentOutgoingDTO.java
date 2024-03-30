package zhelonin.hm3.dto;


public class StudentOutgoingDTO {
  private Integer id;
  private String name;


  public StudentOutgoingDTO() {
  }

  public StudentOutgoingDTO(Integer id, String name
     ) {
    this.id = id;
    this.name = name;

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




  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("StudentOutgoingDTO{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
