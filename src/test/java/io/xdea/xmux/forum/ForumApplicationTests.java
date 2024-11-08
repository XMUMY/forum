package io.xdea.xmux.forum;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SuiteDisplayName("Forum Application Test Suite")
@SelectClasses({
        ForumControllerTest.class,
        ThreadControllerTest.class
})
@SpringBootTest
class ForumApplicationTests {
    @AfterAll
    public static void cleanUpDatabase(@Autowired Flyway flyway) {
        flyway.clean();
    }
}
