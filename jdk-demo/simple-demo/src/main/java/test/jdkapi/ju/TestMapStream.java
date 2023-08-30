package test.jdkapi.ju;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

public class TestMapStream {

    public static void main( String[] args ) {
        List<A> list = new ArrayList<>();
        list.add( new A( "zxf-2", "aa" ) );
        list.add( new A( null, "bb" ) );
        list.add( new A( "zxf-1", null ) );
        Map<String, String> map = list.stream().collect( Collectors.toMap( A::getName, A::getAge ) ); // value 不能为空
        System.out.println( map );
    }

    @Data
    @AllArgsConstructor
    static class A {
        String name;
        String age;
    }

}
