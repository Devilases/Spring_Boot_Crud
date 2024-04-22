package zhelonin.hm3.it.repo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import zhelonin.hm3.entity.Teacher;
import zhelonin.hm3.it.IntegrationBaseTest;
import zhelonin.hm3.repo.boot.TeacherRepository;


@RequiredArgsConstructor
class TeacherRepositoryTest extends IntegrationBaseTest {

    private final TeacherRepository teacherRepository;


    @Test
    void findById(){
      Optional<Teacher> byId = teacherRepository.findById(3);
      assertNotNull(byId);
    }

    @Test
    void delete(){
      teacherRepository.deleteById(2);
      assertTrue(teacherRepository.findById(2).isEmpty());
    }

    @Test
    void save(){
      Teacher teacher = new Teacher(null, "Ser");
      teacherRepository.save(teacher);
      assertNotNull(teacher.getId());
    }

    @Test
    void findAll(){
      List<Teacher> all = teacherRepository.findAll();
      assertEquals(3, all.size());
    }
}
