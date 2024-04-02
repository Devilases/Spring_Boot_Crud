package zhelonin.hm3.mapper;

import zhelonin.hm3.dto.LessonIncomingDTO;
import zhelonin.hm3.dto.LessonOutgoingDTO;
import zhelonin.hm3.dto.LessonUpdateDTO;
import zhelonin.hm3.entity.Lesson;
import zhelonin.hm3.mapper.specification.LessonDtoMapper;
import zhelonin.hm3.mapper.specification.StudentDtoMapper;
import zhelonin.hm3.mapper.specification.TeacherDtoMapper;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LessonDtoMapperImpl implements LessonDtoMapper {
  private final StudentDtoMapper studentMapper;
  private final TeacherDtoMapper teacherDtoMapper;


  public LessonDtoMapperImpl(StudentDtoMapper studentMapper, TeacherDtoMapper teacherDtoMapper) {
    this.studentMapper = studentMapper;
    this.teacherDtoMapper = teacherDtoMapper;
  }

  @Override
  public Lesson map(LessonIncomingDTO lessonIncomingDTO) {
    return new Lesson(
        null,
        lessonIncomingDTO.getName(),
        lessonIncomingDTO.getTeacher(),
        null
    );

  }

  @Override
  public Lesson map(LessonUpdateDTO lessonUpdateDTO) {
    return new Lesson(
        lessonUpdateDTO.getId()
        , lessonUpdateDTO.getName()
        , teacherDtoMapper.map(lessonUpdateDTO.getTeacherUpdateDTO())
        , studentMapper.mapUpdateList(lessonUpdateDTO.getStudentUpdateDTOS())
    );
  }

  @Override
  public LessonOutgoingDTO map(Lesson lesson) {
    return new LessonOutgoingDTO(
        lesson.getId(),
        lesson.getName(),
        teacherDtoMapper.map(lesson.getTeacher()),
        studentMapper.map(lesson.getStudents())
    );
  }

  @Override
  public List<LessonOutgoingDTO> map(List<Lesson> lessons) {
   return lessons.stream().map(this::map).toList();
  }
}
