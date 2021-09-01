package com.archyle.fra.friendlyreminderbackend.input;

import com.archyle.fra.friendlyreminderbackend.output.repository.TableNames;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.testcontainers.containers.PostgreSQLContainer;

public class AbstractIntegrationTest {

  @Autowired private JdbcTemplate jdbcTemplate;

  @BeforeEach
  public void setup() {
    JdbcTestUtils.deleteFromTables(jdbcTemplate, TableNames.TABLE_NAMES.toArray(new String[0]));
  }

  protected static PostgreSQLContainer<?> postgreDBContainer =
      new PostgreSQLContainer<>("postgres:9.4");

  static {
    postgreDBContainer.start();
  }

  protected static class DockerPostgreDataSourceInitializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
          applicationContext,
          "spring.datasource.url=" + postgreDBContainer.getJdbcUrl(),
          "spring.datasource.username=" + postgreDBContainer.getUsername(),
          "spring.datasource.password=" + postgreDBContainer.getPassword());
    }
  }
}
