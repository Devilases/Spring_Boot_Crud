package zhelonin.hm3.unit.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import zhelonin.hm3.dto.LessonIncomingDTO
import zhelonin.hm3.dto.LessonOutgoingDTO
import zhelonin.hm3.dto.LessonUpdateDTO
import zhelonin.hm3.entity.Lesson
import zhelonin.hm3.exception.NotFoundException
import zhelonin.hm3.mapper.specification.LessonDtoMapper
import zhelonin.hm3.repo.boot.LessonRepository
import zhelonin.hm3.service.LessonServiceImpl
import java.util.*
import java.util.List

@ExtendWith(MockitoExtension::class)
internal class LessonServiceImplTest {
    @Mock
    private lateinit var lessonRepo: LessonRepository

    @Mock
    private lateinit var lessonDtoMapper: LessonDtoMapper

    @InjectMocks
    private lateinit var lessonService: LessonServiceImpl
    @Test
    fun save() {
        val lessonIncomingDTO = LessonIncomingDTO("mat", null)
        val lesson = Lesson(1, "mat", null, null)
        val lessonOutgoingDTO = LessonOutgoingDTO(1, "mat", null, null)
        Mockito.doReturn(lesson).`when`(lessonDtoMapper).map(lessonIncomingDTO)
        Mockito.doReturn(lesson).`when`(lessonRepo).save(lesson)
        Mockito.doReturn(lessonOutgoingDTO).`when`(lessonDtoMapper).map(lesson)
        val save = lessonService!!.save(lessonIncomingDTO)
        Assertions.assertEquals(lessonOutgoingDTO, save)
    }

    @Test
    @Throws(NotFoundException::class)
    fun update() {
        val lessonUpdateDTO = LessonUpdateDTO(1, "mat", null, null)
        val lesson = Lesson(1, "mat", null, null)
        Mockito.doReturn(lesson).`when`(lessonDtoMapper).map(lessonUpdateDTO)
        Mockito.doReturn(true).`when`(lessonRepo).existsById(lesson.id)
        lessonService!!.update(lessonUpdateDTO)
        val argumentCaptor = ArgumentCaptor.forClass(Lesson::class.java)
        Mockito.verify(lessonRepo).saveAndFlush(argumentCaptor.capture())
        val value = argumentCaptor.value
        Assertions.assertEquals(lessonUpdateDTO.id, value.id)
    }

    @Test
    @Throws(NotFoundException::class)
    fun findById() {
        val incomingId = 1
        val lessonOptional = Optional.of(Lesson(1, "mat", null, null))
        val lessonOutgoingDTO = LessonOutgoingDTO(1, "mat", null, null)
        Mockito.doReturn(true).`when`(lessonRepo).existsById(incomingId)
        Mockito.doReturn(lessonOptional).`when`(lessonRepo).findById(incomingId)
        Mockito.doReturn(lessonOutgoingDTO).`when`(lessonDtoMapper).map(lessonOptional.get())
        val byId = lessonService!!.findById(incomingId)
        Assertions.assertEquals(incomingId, byId.id)
    }

    @Test
    fun findAll() {
        val lesson = Lesson(1, "mat", null, null)
        val lessonOutgoingDTO = LessonOutgoingDTO(1, "mat", null, null)
        val lessonList = List.of(lesson)
        val lessonOutgoingDTOList = List.of(lessonOutgoingDTO)
        Mockito.doReturn(lessonList).`when`(lessonRepo).findAll()
        Mockito.doReturn(lessonOutgoingDTOList).`when`(lessonDtoMapper).map(lessonList)
        val all = lessonService!!.findAll()
        Mockito.verify(lessonRepo).findAll()
        Mockito.verify(lessonDtoMapper).map(lessonList)
        Assertions.assertEquals(lessonList.size, all.size)
    }

    @Test
    @Throws(NotFoundException::class)
    fun delete() {
        val incomingId = 1
        Mockito.doReturn(true).`when`(lessonRepo).existsById(incomingId)
        lessonService!!.delete(incomingId)
        val argumentCaptor = ArgumentCaptor.forClass(Int::class.java)
        Mockito.verify(lessonRepo).deleteById(argumentCaptor.capture())
        val value = argumentCaptor.value
        Assertions.assertEquals(incomingId, value)
    }
}