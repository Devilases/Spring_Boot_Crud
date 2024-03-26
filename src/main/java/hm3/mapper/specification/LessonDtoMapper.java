package hm3.mapper.specification;

import hm3.dto.LessonIncomingDTO;
import hm3.dto.LessonOutgoingDTO;
import hm3.dto.LessonUpdateDTO;
import hm3.entity.Lesson;
import java.util.List;

public interface LessonDtoMapper {
  Lesson map(LessonIncomingDTO lessonIncomingDTO);

  Lesson map(LessonUpdateDTO lessonUpdateDTO);

  LessonOutgoingDTO map(Lesson lesson);

  List<LessonOutgoingDTO> map(List<Lesson> lessons);

}
