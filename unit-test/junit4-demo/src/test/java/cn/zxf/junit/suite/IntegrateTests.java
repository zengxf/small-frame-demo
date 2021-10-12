package cn.zxf.junit.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith( Suite.class )
@SuiteClasses( { //
        TestPart1.class, //
        TestPart2.class, //
} )
public class IntegrateTests {

}
