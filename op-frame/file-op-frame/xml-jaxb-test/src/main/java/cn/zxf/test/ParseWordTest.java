package cn.zxf.test;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;

import lombok.ToString;

public class ParseWordTest extends ReadXmlUtil {

    public static void main( String[] args ) {
        test1();
    }

    static void test1() {
        JAXBContext jaxbContext;
        try {
            String xmlContent = readXml( "word.xml" );
            StreamSource streamSource = new StreamSource( new StringReader( xmlContent ) );
            // 加载映射bean类
            jaxbContext = JAXBContext.newInstance( Wordbook.class );
            // 创建解析
            Unmarshaller um = jaxbContext.createUnmarshaller();
            Wordbook wordbook = (Wordbook) um.unmarshal( streamSource );
            wordbook.itemList.forEach( item -> {
                System.out.println( item.word );
                System.out.println( item.trans );
                System.out.println("\n---------------------\n");
            } );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @ToString
    @XmlRootElement( name = "wordbook" )
    public static class Wordbook {
        @XmlElement( name = "item" )
        public List<Item> itemList;
    }

    @ToString
    @XmlRootElement( name = "item" )
    public static class Item {
        @XmlElement
        public String  word;
        @XmlElement
        public String  trans;
        @XmlElement
        public String  phonetic;
        @XmlElement
        public String  tags;
        @XmlElement
        public Integer progress;
    }

}
