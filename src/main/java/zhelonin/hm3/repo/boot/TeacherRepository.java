package zhelonin.hm3.repo.boot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zhelonin.hm3.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {


}
