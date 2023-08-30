package test.jvm.memory;

// time & java -Xmx2g -Xms2g -XX:+AlwaysPreTouch cn.simple.test.jvm.memory.TestAlwaysPreTouch & time
// time & java -Xmx2g -Xms2g -XX:-AlwaysPreTouch cn.simple.test.jvm.memory.TestAlwaysPreTouch & time
public class TestAlwaysPreTouch {

    public static void main( String[] args ) {
        System.out.println( "test ok ..." );
    }

}
