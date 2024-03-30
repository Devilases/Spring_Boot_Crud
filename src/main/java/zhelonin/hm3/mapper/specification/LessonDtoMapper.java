package zhelonin.hm3.mapper.specification;

import zhelonin.hm3.dto.LessonIncomingDTO;
import zhelonin.hm3.dto.LessonOutgoingDTO;
import zhelonin.hm3.dto.LessonUpdateDTO;
import zhelonin.hm3.entity.Lesson;
import java.util.List;

public interface LessonDtoMapper {
  Lesson map(LessonIncomingDTO lessonIncomingDTO);

  Lesson map(LessonUpdateDTO lessonUpdateDTO);

  LessonOutgoingDTO map(Lesson lesson);

  List<LessonOutgoingDTO> map(List<Lesson> lessons);

}
