package zhelonin.hm3.it.service

import lombok.RequiredArgsConstructor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import zhelonin.hm3.exception.NotFoundException
import zhelonin.hm3.service.specification.TeacherService

@Transactional
@SpringBootTest
@RequiredArgsConstructor
open class TeacherServiceIT {

    @Autowired
    private lateinit var teacherService: TeacherService

    @Test
    @Throws(NotFoundException::class)
    fun findById() {
        val expectedId = 2
        val byId = teacherService.findById(expectedId)
        Assertions.assertNotNull(byId)
    }
}
