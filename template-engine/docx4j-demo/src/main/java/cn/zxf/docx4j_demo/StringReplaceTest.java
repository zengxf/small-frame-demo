package cn.zxf.docx4j_demo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.relationships.Relationships;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringReplaceTest {

    public static void main( String[] args ) throws JAXBException, Docx4JException, IOException {
        URL url = StringReplaceTest.class.getResource( "/收入证明_template.docx" );
        log.info( "url: {}", url );
        InputStream templateIs = url.openStream();
        String outPath = "C:/Users/Administrator/Desktop/收入证明.docx";
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load( templateIs );
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        Map<String, String> mappings = new HashMap<>();
        mappings.put( "head", "头 - " + LocalDate.now() );
        mappings.put( "tail", "尾 - " + LocalTime.now() );
        mappings.put( "name", "马参军" );
        mappings.put( "years", "5" );
        mappings.put( "post", "攻城狮" );
        mappings.put( "money", "25,000.00" );
        mappings.put( "moneyChinese", "二万五仟圆" );
        mappings.put( "address", "天宫一号" );
        mappings.put( "telephone", "188-6888-8688" );
        mappings.put( "year", "2018" );
        mappings.put( "month", "09" );
        mappings.put( "date", "11" );
        documentPart.variableReplace( mappings );

        // 替换页眉、页脚
        RelationshipsPart rp = documentPart.getRelationshipsPart();
        Relationships relationships = rp.getRelationships();
        List<Relationship> relationship = relationships.getRelationship();
        for ( Relationship r : relationship ) {
            String type = r.getType();
            if ( type.equals( Namespaces.HEADER ) ) {
                HeaderPart head = (HeaderPart) rp.getPart( r );
                head.variableReplace( mappings );
            } else if ( type.equals( Namespaces.FOOTER ) ) {
                FooterPart foot = (FooterPart) rp.getPart( r );
                foot.variableReplace( mappings );
            }
        }

        Docx4J.save( wordMLPackage, new File( outPath ) );
    }

}
