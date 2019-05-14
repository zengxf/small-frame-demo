package cn.zxf.test;

import java.io.Serializable;
import java.lang.reflect.Constructor;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisSerializer;
import org.objenesis.instantiator.ObjectInstantiator;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.reflect.ReflectionFactory;

@Slf4j
public class TestMain {

    @Data
    static class TestInnerDto implements Serializable {
        private static final long serialVersionUID = 1L;
        private String            name;
        private Integer           age;

        public TestInnerDto( String name, Integer age ) {
            this.name = name;
            this.age = age;
            log.info( "TestInnerDto 构造器被调用" );
        }
    }

    @SuppressWarnings( "unchecked" )
    public static void main( String[] args ) throws Exception {
        TestInnerDto dto = new TestInnerDto( "zxf", 33 );
        log.info( "new-dto: {} \n", dto );

        // Objenesis objenesis = new ObjenesisStd();
        Objenesis objenesis = new ObjenesisSerializer();
        ObjectInstantiator<TestInnerDto> instantiator = objenesis.getInstantiatorOf( TestInnerDto.class );
        dto = instantiator.newInstance();
        dto.setName( "new-test" );
        log.info( "objenesis-dto: {} \n", dto );
        dto = objenesis.newInstance( TestInnerDto.class ); // 直接获取实例，内部会缓存
        dto.setName( "new-test-1" );
        log.info( "objenesis-dto-1: {} \n", dto );

        Constructor<TestInnerDto> constructor = TestInnerDto.class.getConstructor( String.class, Integer.class );
        dto = constructor.newInstance( "test", 55 );
        log.info( "constructor-dto: {} \n", dto );

        try {
            constructor = TestInnerDto.class.getConstructor();
            log.info( "constructor: {}", constructor );
        } catch ( Exception e ) {
            log.error( "没有默认的控制器 \n" );
        }

        // 原理
        ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
        constructor = (Constructor<TestInnerDto>) rf.newConstructorForSerialization( TestInnerDto.class );
        dto = constructor.newInstance( (Object[]) null );
        dto.setName( "constructor-test-1" );
        log.info( "原理 - constructor-dto-1: {}", dto );
    }

}
