package cn.zxf.jol.test_size;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openjdk.jol.info.GraphLayout;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestMapSize {

    public static void main( String[] args ) {
        Map<String, List<Vo>> map = IntStream.rangeClosed( 1, 15 ) //
                .mapToObj( i -> {
                    Vo vo = Vo.builder() //
                            .userId( "zxf-0" + i ) //
                            .timestamp( System.currentTimeMillis() ) //
                            .date( "2017-01-05" ) //
                            .group( "org-0" + i % 5 ) //
                            .build();
                    return vo;
                } ) //
                .collect( Collectors.groupingBy( Vo::getGroup ) );

        map.forEach( ( k, v ) -> log.info( "k: {}, v: {}", k, v ) );
        log.info( "\n-----------------\n-----------------\n-----------------" );

        GraphLayout parse = GraphLayout.parseInstance( map );
        log.info( "\ntoPrintable: \n{}", parse.toPrintable() );
        log.info( "\ntoFootprint: \n{}", parse.toFootprint() );
        log.info( "\ntotalCount: {}", parse.totalCount() );
        log.info( "\ntotalSize: {}", parse.totalSize() );
    }

    @Data
    @Builder
    static class Vo {
        String userId;
        long   timestamp;
        String date;
        String group;
    }
}
