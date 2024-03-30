package zhelonin.hm3.service;

import zhelonin.hm3.dto.LessonIncomingDTO;
import zhelonin.hm3.dto.LessonOutgoingDTO;
import zhelonin.hm3.dto.LessonUpdateDTO;
import zhelonin.hm3.entity.Lesson;
import zhelonin.hm3.exception.NotFoundException;
import zhelonin.hm3.mapper.specification.LessonDtoMapper;
import zhelonin.hm3.repo.specification.LessonRepo;
import zhelonin.hm3.service.specification.LessonServiceInter;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LessonService implements LessonServiceInter {

  private LessonRepo lessonRepo;

  private LessonDtoMapper lessonDtoMapper;


  public LessonService(LessonRepo lessonRepo, LessonDtoMapper lessonDtoMapper) {
    this.lessonRepo = lessonRepo;
    this.lessonDtoMapper = lessonDtoMapper;
  }

  private void checkLessonExist(Integer lessonId) throws NotFoundException {
    if (!lessonRepo.exitsById(lessonId)) {
      throw new NotFoundException("Lesson not found.");
    }
  }

  @Override
  public LessonOutgoingDTO save(LessonIncomingDTO lessonInDto) {
    Lesson lesson = lessonDtoMapper.map(lessonInDto);
    lesson = lessonRepo.save(lesson);
    return lessonDtoMapper.map(lesson);
  }

  @Override
  public void update(LessonUpdateDTO lessonUpdateDTO) throws NotFoundException {
    checkLessonExist(lessonUpdateDTO.getId());
    Lesson lesson = lessonDtoMapper.map(lessonUpdateDTO);
    lessonRepo.update(lesson);
  }

  @Override
  public LessonOutgoingDTO findById(Integer lessonId) throws NotFoundException {
    checkLessonExist(lessonId);
    Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(()-> new NotFoundException("Lesson not found."));
    return lessonDtoMapper.map(lesson);
  }

  @Override
  public List<LessonOutgoingDTO> findAll() {
    List<Lesson> lessons = lessonRepo.findAll();
    return lessonDtoMapper.map(lessons);
  }

  @Override
  public void delete(Integer lessonId) throws NotFoundException {
    checkLessonExist(lessonId);
    lessonRepo.deleteById(lessonId);
  }
}
