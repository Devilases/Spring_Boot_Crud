package zhelonin.hm3.service.specification;

import zhelonin.hm3.dto.TeacherIncomingDTO;
import zhelonin.hm3.dto.TeacherOutgoingDTO;
import zhelonin.hm3.dto.TeacherUpdateDTO;
import zhelonin.hm3.exception.NotFoundException;
import java.util.List;

public interface TeacherServiceInter {

  TeacherOutgoingDTO save(TeacherIncomingDTO teacherDto);

  void update(TeacherUpdateDTO teacherDto) throws NotFoundException;

  TeacherOutgoingDTO findById(Integer teacherId) throws NotFoundException;

  List<TeacherOutgoingDTO> findAll();

  boolean delete(Integer teacherId) throws NotFoundException;

}
