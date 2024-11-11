package io.xdea.xmux.forum;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Forum Application Test Suite")
@SelectClasses({
        ForumControllerTest.class,
        ThreadControllerTest.class
})
class ForumApplicationTests {
}
