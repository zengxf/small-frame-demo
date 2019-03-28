package cn.zxf.kryo.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.FastInput;
import com.esotericsoftware.kryo.io.FastOutput;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class TestSerialize {

    static Kryo   k    = new Kryo();
    static String file = "C:\\Users\\Administrator\\Desktop\\aa\\test.bin";

    public static void main( String[] args ) throws FileNotFoundException {
        UserVo vo = data();
        System.out.println( "vo: " + vo );
        OutputStream os = new FileOutputStream( file );
        Output out = new FastOutput( os );
        k.writeClassAndObject( out, vo );
        out.close();

        InputStream is = new FileInputStream( file );
        Input in = new FastInput( is );
        Object obj = k.readClassAndObject( in );
        UserVo readVo = (UserVo) obj;
        System.out.println( "read: " + readVo );
    }

    static UserVo data() {
        UserVo vo = UserVo.builder() //
                .id( 22L ) //
                .name( "zxf" ) //
                .floatV( 23.3F ) //
                .doubleV( 34.3D ) //
                .shortV( (short) 10000 ) //
                .createDate( new Date( 1520332525102L ) ) // 18:20:43
                .money( BigDecimal.valueOf( 10000.12D ) ) //
                .tags( Stream.of( "test", "dev" ).collect( Collectors.toList() ) ) //
                .build();
        return vo;
    }

}
