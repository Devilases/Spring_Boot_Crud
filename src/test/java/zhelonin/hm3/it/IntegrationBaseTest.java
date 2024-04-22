package zhelonin.hm3.it;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;



@SpringBootTest
@Transactional
@Sql({
    "classpath:sql/data.sql"
})
public abstract class IntegrationBaseTest {

  private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1");

  @BeforeAll
  static void runContainer() {
    container.start();
  }

//  @DynamicPropertySource
//  static void postgresProperties(DynamicPropertyRegistry registry) {
//    registry.add("spring.datasource.url", container::getJdbcUrl);
//  }
}
