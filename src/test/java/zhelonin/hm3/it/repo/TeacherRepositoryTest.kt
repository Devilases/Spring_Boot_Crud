package zhelonin.hm3.it.repo

import lombok.RequiredArgsConstructor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import zhelonin.hm3.entity.Teacher
import zhelonin.hm3.it.IntegrationBaseTest
import zhelonin.hm3.repo.boot.TeacherRepository


internal class TeacherRepositoryTest : IntegrationBaseTest() {
    @Autowired
    private lateinit var teacherRepository: TeacherRepository


    @Test
    fun findById() {
        val byId = teacherRepository!!.findById(3)
        Assertions.assertNotNull(byId)
    }

    @Test
    fun delete() {
        teacherRepository!!.deleteById(2)
        Assertions.assertTrue(teacherRepository.findById(2).isEmpty)
    }

    @Test
    fun save() {
        val teacher = Teacher(null, "Ser")
        teacherRepository!!.save(teacher)
        Assertions.assertNotNull(teacher.id)
    }

    @Test
    fun findAll() {
        val all = teacherRepository!!.findAll()
        Assertions.assertEquals(3, all.size)
    }
}
