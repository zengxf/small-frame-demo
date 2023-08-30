package test.new_features.jdk1_8.stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestCollector {
    public static void main( String[] args ) {
        testBase();
    }
    
    static void testStats() {
        List<UserBo> data = new ArrayList<>();
        data.add( UserBo.builder().name( "zxf-01" ).lv1( "a" ).timestamp( 2000 ).build() );
        data.add( UserBo.builder().name( "zxf-01" ).lv1( "b" ).timestamp( 1000 ).build() );
        data.add( UserBo.builder().name( "zxf-02" ).lv1( "c" ).timestamp( 3000 ).build() );
        data.add( UserBo.builder().name( "zxf-02" ).lv1( "d" ).timestamp( 2000 ).build() );

        Collector<UserBo, AtomicInteger, AtomicInteger> c = Collector.of( AtomicInteger::new, ( a, t ) -> a.incrementAndGet(), //
                ( a1, a2 ) -> {
                    a1.addAndGet( a2.get() );
                    return a1;
                } );
        Map<String, AtomicInteger> map = data.stream().collect( Collectors.groupingBy( UserBo::getName, HashMap::new, c ) );
        System.out.println( map );
    }

    static void testBase() {
        Integer size = Stream.of( "a", "b" ).collect( collectingAndThen( toList(), List::size ) );
        log.info( "size: {}", size );
        System.out.println( "----------" );

        Map<?, ?> booleanMap = Stream.of( "a", "b", "c1" ).collect( Collectors.partitioningBy( s -> s.length() > 1 ) );
        log.info( "map: {}", booleanMap );
        System.out.println( "----------" );
    }
    
    @Builder
    @Data
    static class UserBo implements Comparable<UserBo> {
        String name;
        String lv1;
        int    timestamp;

        @Override
        public int compareTo( UserBo o ) {
            int t1 = this.timestamp;
            int t2 = o.timestamp;
            return t1 > t2 ? 1 : t1 == t2 ? 0 : -1;
        }
    }
    
}
