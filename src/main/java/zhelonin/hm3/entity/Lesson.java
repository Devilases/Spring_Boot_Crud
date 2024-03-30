package zhelonin.hm3.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "lesson")
public class Lesson {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  @ManyToOne
  @JoinColumn
  private Teacher teacher;
  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(name = "lesson_student",
      joinColumns = @JoinColumn(name = "lesson_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
  private List<Student> students;

  public Lesson() {
  }

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
    if (students == null) {
      students = new ArrayList<>();
    }
    return students;
  }

  public void addStudent(Student student) {
    students.add(student);
  }

  public void deleteStudent(Student student) {
    students.remove(student);
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Lesson lesson = (Lesson) o;

    if (!Objects.equals(id, lesson.id)) {
      return false;
    }
    if (!Objects.equals(name, lesson.name)) {
      return false;
    }
    if (!Objects.equals(teacher, lesson.teacher)) {
      return false;
    }
    return Objects.equals(students, lesson.students);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
    result = 31 * result + (students != null ? students.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Lesson{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", teacher=").append(teacher);
    sb.append('}');
    return sb.toString();
  }
}
