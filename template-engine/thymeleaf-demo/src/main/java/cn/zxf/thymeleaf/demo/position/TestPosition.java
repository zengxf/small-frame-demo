package cn.zxf.thymeleaf.demo.position;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class TestPosition {

    public static void main( String[] args ) throws IOException {
        InputStream is = TestPosition.class.getResourceAsStream( "/template/position.html" );
        int len = is.available();
        System.out.println( "template len: " + len );
        System.out.println( "-----------" );

        byte[] bs = new byte[len];
        is.read( bs, 0, len );
        String template = new String( bs );
        System.out.println( template );
        System.out.println( "------------" );

        List<PositionDto> list = data();
        TemplateEngine eng = new TemplateEngine();
        Context ctx = new Context();
        ctx.setVariable( "date", "2018-05-24" );
        ctx.setVariable( "website", "http://www.baidu.com" );
        ctx.setVariable( "size", list.size() );
        ctx.setVariable( "list", list );
        String content = eng.process( template, ctx );
        System.out.println( content );
        System.out.println( "------------" );
    }

    static List<PositionDto> data() {
        return IntStream.of( 23, 34, 65, 67 )
                .boxed()
                .map( i -> PositionDto.builder()
                        .id( "test-" + i )
                        .name( "java-dev-" + i )
                        .companyName( "baidu-" + i )
                        .date( "2018-05-" + i )
                        .build() )
                .collect( Collectors.toList() );
    }

}
