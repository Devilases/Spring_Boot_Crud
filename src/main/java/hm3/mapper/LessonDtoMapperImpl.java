package hm3.mapper;

import hm3.dto.LessonIncomingDTO;
import hm3.dto.LessonOutgoingDTO;
import hm3.dto.LessonUpdateDTO;
import hm3.entity.Lesson;
import hm3.mapper.specification.LessonDtoMapper;
import hm3.mapper.specification.StudentDtoMapper;
import hm3.mapper.specification.TeacherDtoMapper;
import java.util.List;

public class LessonDtoMapperImpl implements LessonDtoMapper {
  private static final StudentDtoMapper studentMapper = StudentDtoMapperImpl.getInstance();
  private static final TeacherDtoMapper teacherDtoMapper = TeacherDtoMapperIml.getInstance();

  private static LessonDtoMapper instance;

  private LessonDtoMapperImpl() {
  }

  public static synchronized LessonDtoMapper getInstance() {
    if (instance == null) {
      instance = new LessonDtoMapperImpl();
    }
    return instance;
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
