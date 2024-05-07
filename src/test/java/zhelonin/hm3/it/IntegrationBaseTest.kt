package zhelonin.hm3.it

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest
@Transactional
@Sql("classpath:sql/data.sql")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class IntegrationBaseTest {
    companion object {
        private val container: PostgreSQLContainer<*> = MyPostgreSQLContainer("postgres:14.1")
         //  @DynamicPropertySource
        //  static void postgresProperties(DynamicPropertyRegistry registry) {
        //    registry.add("spring.datasource.url", container::getJdbcUrl);
        //  }
    }
    @BeforeAll
    fun runContainer(): Unit {
        container.start()
    }
}
