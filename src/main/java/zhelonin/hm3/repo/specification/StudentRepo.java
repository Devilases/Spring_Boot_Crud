package zhelonin.hm3.repo.specification;

import zhelonin.hm3.entity.Lesson;
import zhelonin.hm3.entity.Student;
import java.util.List;

public interface StudentRepo extends Repository<Student,Integer> {
  List<Lesson> findLessonByStudentId(Integer stdId);

}
