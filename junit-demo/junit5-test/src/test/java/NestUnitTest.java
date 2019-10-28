import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName( "内嵌测试类" )
public class NestUnitTest {
    @BeforeEach
    void init() {
        System.out.println( "\n测试方法执行前准备" );
    }

    @Nested
    @DisplayName( "第一个内嵌测试类" )
    class FirstNestTest {
        @Test
        void test() {
            System.out.println( "第一个内嵌测试类执行测试" );
        }
    }

    @Nested
    @DisplayName( "第二个内嵌测试类" )
    class SecondNestTest {
        @Test
        void test() {
            System.out.println( "第二个内嵌测试类执行测试" );
        }
    }
}
