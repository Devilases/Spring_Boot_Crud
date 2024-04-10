package zhelonin.hm3.repo.boot;

import org.springframework.data.jpa.repository.JpaRepository;
import zhelonin.hm3.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
