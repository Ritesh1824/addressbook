package com.jemmic.addressbook;

import com.jemmic.addressbook.controller.UserActionControllerAddContactTest;
import com.jemmic.addressbook.controller.UserActionControllerDisplayContactTest;
import com.jemmic.addressbook.controller.UserActionControllerRemoveContactTest;
import com.jemmic.addressbook.controller.UserActionControllerUpdateContactTest;
import com.jemmic.addressbook.exception.InvalidUserInputExceptionTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserActionControllerAddContactTest.class,
        UserActionControllerDisplayContactTest.class,
        UserActionControllerRemoveContactTest.class,
        UserActionControllerUpdateContactTest.class,
        InvalidUserInputExceptionTest.class
})
public class JunitTestSuite {
}
