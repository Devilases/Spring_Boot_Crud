package hm3.service;

import hm3.dto.LessonIncomingDTO;
import hm3.dto.LessonOutgoingDTO;
import hm3.dto.LessonUpdateDTO;
import hm3.entity.Lesson;
import hm3.exception.NotFoundException;
import hm3.mapper.LessonDtoMapperImpl;
import hm3.mapper.specification.LessonDtoMapper;
import hm3.repo.LessonRepository;

import hm3.repo.specification.LessonRepo;

import hm3.service.specification.LessonServiceInter;

import java.util.List;


public class LessonService implements LessonServiceInter {

  private LessonRepo lessonRepo = LessonRepository.getInstance();

  private LessonDtoMapper lessonDtoMapper = LessonDtoMapperImpl.getInstance();

  private static LessonServiceInter instance;

  private LessonService() {
  }

  public static synchronized LessonServiceInter getInstance() {
    if (instance == null) {
      instance = new LessonService();
    }
    return instance;
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
