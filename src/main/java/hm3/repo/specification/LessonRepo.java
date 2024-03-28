package hm3.repo.specification;

import hm3.entity.Lesson;
import hm3.entity.Student;
import java.util.List;

public interface LessonRepo extends Repository<Lesson, Integer> {

   List<Student> findStudentByLessonId(Integer lessonId);


}
