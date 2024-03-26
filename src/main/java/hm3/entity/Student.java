package hm3.entity;

import hm3.repo.LessonToStudentRepository;
import hm3.repo.specification.LessonToStudentRepo;
import java.util.List;
import java.util.Objects;

public class Student {
  private static LessonToStudentRepo lessonToStudentRepo = LessonToStudentRepository.getInstance();

  private Integer id;
  private String name;
  private List<Lesson> lessons;

  public Student(Integer id, String name, List<Lesson> list) {
    this.id = id;
    this.name = name;
    this.lessons = list;
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

  public List<Lesson> getLessons() {
    if(lessons == null){
      lessons = lessonToStudentRepo.findLessonByStudentId(this.id);
    }
    return lessons;
  }

  public void setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Student student = (Student) o;

    if (!Objects.equals(id, student.id)) {
      return false;
    }
    if (!Objects.equals(name, student.name)) {
      return false;
    }
    return Objects.equals(lessons, student.lessons);

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (lessons != null ? lessons.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Student{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", list=").append(lessons);
    sb.append('}');
    return sb.toString();
  }
}
