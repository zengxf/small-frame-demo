package cn.zxf.test;

import java.io.File;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;

import lombok.ToString;

public class ParseUserTest extends ReadXmlUtil {

    public static void main( String[] args ) {
        testUser();
    }

    static void testUser() {
        JAXBContext jaxbContext;
        try {
            String xmlContent = readXml( "user.xml" );

            // 加载映射bean类
            jaxbContext = JAXBContext.newInstance( User.class );
            // 创建解析
            Unmarshaller um = jaxbContext.createUnmarshaller();
            StreamSource streamSource = new StreamSource( new StringReader( xmlContent ) );
            User root = (User) um.unmarshal( streamSource );
            System.out.println( root );

            jaxbContext.createMarshaller().marshal( root, new File( "D:/user.xml" ) );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @ToString
    @XmlRootElement( name = "user" )
    public static class User {

        public String name;
        @XmlElement( name = "token" )
        public String pwd;
        // public String token;
        public int    age;
        public String address;
        public String note;

    }

}
