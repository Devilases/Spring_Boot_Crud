package hm3.entity;

import hm3.repo.LessonToStudentRepository;
import hm3.repo.specification.LessonToStudentRepo;
import java.util.List;
import java.util.Objects;

public class Lesson {

  private static LessonToStudentRepo lessonToStudentRepo = LessonToStudentRepository.getInstance();

  private Integer id;
  private String name;
  private Teacher teacher;

  private List<Student> students;

  public Lesson(Integer id, String name, Teacher teacher, List<Student> students) {
    this.id = id;
    this.name = name;
    this.teacher = teacher;
    this.students = students;
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

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public List<Student> getStudents() {
    if(students == null){
      students = lessonToStudentRepo.findStudentByLessonId(this.id);
    }
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Lesson{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", teacher=").append(teacher);
    sb.append(", students=").append(students);
    sb.append('}');
    return sb.toString();
  }
}
