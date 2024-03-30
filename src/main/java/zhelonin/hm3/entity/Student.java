package zhelonin.hm3.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "student")
public class Student {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(name = "lesson_student",
      joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "lesson_id", referencedColumnName = "id"))
  private List<Lesson> lessons;

  public Student() {
  }

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
    if (lessons == null) {
      lessons = new ArrayList<>();
    }
    return lessons;
  }

  public void addLesson(Lesson lesson) {
    lessons.add(lesson);
  }

  public void deleteLesson(Lesson lesson) {
    int index = -1;
    for (Lesson lesson1 : lessons) {
      if (Objects.equals(lesson.getId(), lesson1.getId())) {
        index = lessons.indexOf(lesson1);
      }
    }
    if (index >= 0) {
      lessons.remove(index);
    }
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
    sb.append(", lessons=").append(lessons);
    sb.append('}');
    return sb.toString();
  }
}
