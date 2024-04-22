package zhelonin.hm3.unit.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import zhelonin.hm3.dto.LessonIncomingDTO;
import zhelonin.hm3.dto.LessonOutgoingDTO;
import zhelonin.hm3.dto.LessonUpdateDTO;
import zhelonin.hm3.entity.Lesson;
import zhelonin.hm3.exception.NotFoundException;
import zhelonin.hm3.mapper.specification.LessonDtoMapper;
import zhelonin.hm3.repo.boot.LessonRepository;
import zhelonin.hm3.service.LessonServiceImpl;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {

  @Mock
  private LessonRepository lessonRepo;
  @Mock
  private LessonDtoMapper lessonDtoMapper;
  @InjectMocks
  private LessonServiceImpl lessonService;

  @Test
  void save() {
    LessonIncomingDTO lessonIncomingDTO = new LessonIncomingDTO("mat", null);
    Lesson lesson = new Lesson(1, "mat", null, null);
    LessonOutgoingDTO lessonOutgoingDTO = new LessonOutgoingDTO(1, "mat", null, null);
    Mockito.doReturn(lesson).when(lessonDtoMapper).map(lessonIncomingDTO);
    Mockito.doReturn(lesson).when(lessonRepo).save(lesson);
    Mockito.doReturn(lessonOutgoingDTO).when(lessonDtoMapper).map(lesson);
    LessonOutgoingDTO save = lessonService.save(lessonIncomingDTO);
    assertEquals(lessonOutgoingDTO, save);
  }

  @Test
  void update() throws NotFoundException {
    LessonUpdateDTO lessonUpdateDTO = new LessonUpdateDTO(1,"mat",null, null);
    Lesson lesson = new Lesson(1, "mat", null, null);
    Mockito.doReturn(lesson).when(lessonDtoMapper).map(lessonUpdateDTO);
    Mockito.doReturn(true).when(lessonRepo).existsById(lesson.getId());

    lessonService.update(lessonUpdateDTO);
    ArgumentCaptor<Lesson> argumentCaptor = ArgumentCaptor.forClass(Lesson.class);

    Mockito.verify(lessonRepo).saveAndFlush(argumentCaptor.capture());
    Lesson value = argumentCaptor.getValue();
    assertEquals(lessonUpdateDTO.getId(), value.getId());
  }

  @Test
  void findById() throws NotFoundException {
    Integer incomingId = 1;
    Optional<Lesson> lessonOptional = Optional.of(new Lesson(1, "mat", null, null));
    LessonOutgoingDTO lessonOutgoingDTO = new LessonOutgoingDTO(1, "mat", null, null);

    Mockito.doReturn(true).when(lessonRepo).existsById(incomingId);
    Mockito.doReturn(lessonOptional).when(lessonRepo).findById(incomingId);
    Mockito.doReturn(lessonOutgoingDTO).when(lessonDtoMapper).map(lessonOptional.get());

    LessonOutgoingDTO byId = lessonService.findById(incomingId);

    assertEquals(incomingId, byId.getId());
  }

  @Test
  void findAll() {
    Lesson lesson = new Lesson(1, "mat", null, null);
    LessonOutgoingDTO lessonOutgoingDTO = new LessonOutgoingDTO(1, "mat", null, null);
    List<Lesson> lessonList = List.of(lesson);
    List<LessonOutgoingDTO> lessonOutgoingDTOList = List.of(lessonOutgoingDTO);

    Mockito.doReturn(lessonList).when(lessonRepo).findAll();
    Mockito.doReturn(lessonOutgoingDTOList).when(lessonDtoMapper).map(lessonList);

    List<LessonOutgoingDTO> all = lessonService.findAll();

    Mockito.verify(lessonRepo).findAll();
    Mockito.verify(lessonDtoMapper).map(lessonList);
    assertEquals(lessonList.size(), all.size());
  }

  @Test
  void delete() throws NotFoundException {
    Integer incomingId = 1;

    Mockito.doReturn(true).when(lessonRepo).existsById(incomingId);

    lessonService.delete(incomingId);

    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    Mockito.verify(lessonRepo).deleteById(argumentCaptor.capture());
    Integer value = argumentCaptor.getValue();

    assertEquals(incomingId, value);
  }
}