package test;

import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class UserAnnotationTest {

    public static void main( String[] args ) {
        UserAnnotation UserAnnotation = new UserAnnotation().setName( "zxf" )
                .setAge( 22 )
                .setAddress( "湖南" )
                .setNote( "dd" )
                .setTags( List.of( "t1", "b2" ) );
        XStream xstream = new XStream();
        xstream.processAnnotations( UserAnnotation.class ); // 应用类的注解
        xstream.autodetectAnnotations( true ); // 自动检测注解

        String xml = xstream.toXML( UserAnnotation ); // XML 序列化
        System.out.println( xml );

        UserAnnotation xmlUserAnnotation = (UserAnnotation) xstream.fromXML( xml ); // XML 反序列化
        System.out.println( xmlUserAnnotation );

        // ----
        // JSON 开始
        // ----

        xstream = new XStream( new JettisonMappedXmlDriver() );
        xstream.setMode( XStream.NO_REFERENCES );

        String json = xstream.toXML( UserAnnotation ); // JSON 序列化
        System.out.println( json );

        UserAnnotation jsonUserAnnotation = (UserAnnotation) xstream.fromXML( json ); // JSON 反序列
        System.out.println( jsonUserAnnotation );
    }

}
