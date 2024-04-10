package zhelonin.hm3.repo.boot;

import org.springframework.data.jpa.repository.JpaRepository;
import zhelonin.hm3.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

}
