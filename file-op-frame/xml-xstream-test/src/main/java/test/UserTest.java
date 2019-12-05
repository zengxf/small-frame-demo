package test;

import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class UserTest {

    public static void main( String[] args ) {
        User user = new User().setName( "zxf" )
                .setAge( 22 )
                .setAddress( "湖南" )
                .setNote( "dd" )
                .setTags( List.of( "t1", "b2" ) );
        XStream xstream = new XStream();
        xstream.aliasPackage( "测试", "test" ); // 为包名称重命名
        xstream.alias( "用户", User.class ); // 为类名节点重命名
        xstream.aliasField( "姓名", User.class, "name" ); // 为类的字段节点重命名
        xstream.addImplicitCollection( User.class, "tags" ); // 省略集合节点
        xstream.useAttributeFor( User.class, "age" ); // 把字段节点设置成属性
        xstream.omitField( User.class, "note" ); // 把字段节点隐藏

        String xml = xstream.toXML( user ); // XML 序列化
        System.out.println( xml );

        User xmlUser = (User) xstream.fromXML( xml ); // XML 反序列化
        System.out.println( xmlUser );

        // ----
        // JSON 开始
        // ----

        xstream = new XStream( new JettisonMappedXmlDriver() );
        xstream.setMode( XStream.NO_REFERENCES );

        String json = xstream.toXML( user ); // JSON 序列化
        System.out.println( json );

        User jsonUser = (User) xstream.fromXML( json ); // JSON 反序列
        System.out.println( jsonUser );
    }

}
