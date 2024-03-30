package zhelonin.hm3.service.specification;

import zhelonin.hm3.dto.LessonIncomingDTO;
import zhelonin.hm3.dto.LessonOutgoingDTO;
import zhelonin.hm3.dto.LessonUpdateDTO;
import zhelonin.hm3.exception.NotFoundException;
import java.util.List;

public interface LessonServiceInter {

  LessonOutgoingDTO save(LessonIncomingDTO LessonInDto);

  void update(LessonUpdateDTO empDto) throws NotFoundException;

  LessonOutgoingDTO findById(Integer lessonId) throws NotFoundException;

  List<LessonOutgoingDTO> findAll();

  void delete(Integer lessonId) throws NotFoundException;

}
