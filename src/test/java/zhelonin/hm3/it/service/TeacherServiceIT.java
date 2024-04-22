package zhelonin.hm3.it.service;

import static org.junit.jupiter.api.Assertions.*;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zhelonin.hm3.dto.TeacherOutgoingDTO;
import zhelonin.hm3.exception.NotFoundException;
import zhelonin.hm3.service.specification.TeacherService;

@Transactional
@SpringBootTest
@RequiredArgsConstructor
class TeacherServiceIT {

  private final TeacherService teacherService;

  @Test
  void findById() throws NotFoundException {
    Integer expectedId = 2;
    TeacherOutgoingDTO byId = teacherService.findById(expectedId);
    assertNotNull(byId);
  }
}
