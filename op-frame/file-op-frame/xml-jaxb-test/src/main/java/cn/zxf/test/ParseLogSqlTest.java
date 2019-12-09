package cn.zxf.test;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.transform.stream.StreamSource;

import lombok.ToString;

public class ParseLogSqlTest extends ReadXmlUtil {

    public static void main( String[] args ) {
        test1();
    }

    static void test1() {
        JAXBContext jaxbContext;
        try {
            String xmlContent = readXml( "log-sql.xml" );
            StreamSource streamSource = new StreamSource( new StringReader( xmlContent ) );

            // 加载映射bean类
            jaxbContext = JAXBContext.newInstance( Log.class );
            // 创建解析
            Unmarshaller um = jaxbContext.createUnmarshaller();
            Log Log = (Log) um.unmarshal( streamSource );
            System.out.println( Log );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @ToString
    @XmlRootElement( name = "Log" )
    public static class Log {
        @XmlAttribute( name = "Remark" )
        public String      Remark;
        @XmlAttribute
        public String      pri;
        @XmlAttribute
        public String      tablename;
        @XmlElement
        public RemarkValue LogID;
        @XmlElement
        public Subject     Subject;
        @XmlElement
        public SqlObject   Object;
        @XmlElement
        public RemarkValue Details;
        @XmlElement
        public RemarkValue Result;
        @XmlElement
        public RemarkValue EntryStamp;
        @XmlElement
        public RemarkValue Level;
        @XmlElement
        public RemarkValue Type;
        @XmlElement
        public RemarkValue ProductType;
        @XmlElement
        public RemarkValue BehaviourType;
        @XmlElement
        public RemarkValue Reservation;
    }

    @ToString
    @XmlRootElement( name = "Object" )
    public static class SqlObject {
        @XmlAttribute( name = "Remark" )
        public String      Remark;
        @XmlElement
        public RemarkValue ObjectName;
    }

    @ToString
    @XmlRootElement( name = "Subject" )
    public static class Subject {
        @XmlAttribute( name = "Remark" )
        public String            Remark;
        @XmlElement
        public List<RemarkValue> Node;
        @XmlElement
        public RemarkValue       ComputerName;
        @XmlElement
        public RemarkValue       UserName;
        @XmlElement
        public RemarkValue       Department;
        @XmlElement
        public RemarkValue       Program;
        @XmlElement
        public RemarkValue       Facility;
    }

    // -----

    public static class RemarkValue {
        @XmlAttribute( name = "Remark" )
        public String Remark;
        @XmlValue
        public String value;

        @Override
        public String toString() {
            return "{Remark=" + Remark + ", value=" + value + "}";
        }
    }

}
