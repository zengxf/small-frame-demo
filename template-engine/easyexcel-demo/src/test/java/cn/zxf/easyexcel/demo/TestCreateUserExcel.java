package cn.zxf.easyexcel.demo;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.alibaba.excel.EasyExcel;

public class TestCreateUserExcel {

    public static void main( String[] args ) {
        long start = System.currentTimeMillis();

        String fileName = "C:\\Users\\Administrator\\Desktop\\aa\\user.xlsx";
        EasyExcel.write( fileName, UserDto.class )
                .sheet( "用户-数据" )
                .doWrite( data() );

        System.out.println( "OK! 用时： " + ( System.currentTimeMillis() - start ) + " ms" );
    }

    static List<UserDto> data() {
        return IntStream.range( 1, 11 )
                .boxed()
                .map( i -> UserDto.builder()
                        .name( "user=" + i )
                        .age( 22 + i )
                        .createDate( new Date( System.currentTimeMillis() + i * 24L * 3600 * 1000 ) )
                        .build() )
                .collect( Collectors.toList() );
    }

}
