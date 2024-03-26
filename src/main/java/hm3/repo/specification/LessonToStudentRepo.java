package hm3.repo.specification;

import hm3.entity.Lesson;
import hm3.entity.LessonToStudentReservation;
import hm3.entity.Student;
import java.util.List;
import java.util.Optional;

public interface LessonToStudentRepo extends Repository<LessonToStudentReservation,Integer> {

  boolean deleteByStudentId(Integer stdId);

  boolean deleteByLessonId(Integer lessonId);

  List<LessonToStudentReservation> findAllByStudentId(Integer stdId);

  List<Lesson> findLessonByStudentId(Integer stdId);

  List<LessonToStudentReservation> findAllByLessonId(Integer lessonId);

  List<Student> findStudentByLessonId(Integer lessonId);

  Optional<LessonToStudentReservation> findByStudentIdAndLessonId(Integer stdId, Integer lessonId);

}
