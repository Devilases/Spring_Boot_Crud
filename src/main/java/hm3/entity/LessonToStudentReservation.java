package hm3.entity;

public class LessonToStudentReservation {

  private Integer id;
  private Integer lessonId;
  private Integer studentId;

  public LessonToStudentReservation(Integer id, Integer lessonId, Integer studentId) {
    this.id = id;
    this.lessonId = lessonId;
    this.studentId = studentId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getLessonId() {
    return lessonId;
  }

  public void setLessonId(Integer lessonId) {
    this.lessonId = lessonId;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public void setStudentId(Integer studentId) {
    this.studentId = studentId;
  }
}
