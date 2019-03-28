package cn.zxf.docx4j_demo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.xml.bind.JAXBException;

import org.docx4j.Docx4J;
import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.finders.ClassFinder;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tr;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamicTableTest {

    public static void main( String[] args ) throws JAXBException, Docx4JException, IOException {
        URL url = DynamicTableTest.class.getResource( "/表格_template.docx" );
        log.info( "url: {}", url );
        InputStream templateIs = url.openStream();
        String outPath = "C:/Users/Administrator/Desktop/表格.docx";
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load( templateIs );
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        { // 构造循环列表的数据
            ClassFinder find = new ClassFinder( Tbl.class );
            new TraversalUtil( documentPart.getContent(), find );
            Tbl table = (Tbl) find.results.get( 1 ); // 第二个表
            List<Object> trList = table.getContent();
            Tr dynamicTr = (Tr) trList.get( 1 ); // 第二行约定为模板
            String dynamicTrXml = XmlUtils.marshaltoString( dynamicTr ); // 获取模板行的 xml 数据
            log.info( "dynamic-tr-xml: {}", dynamicTrXml );
            List<Map<String, Object>> dataList = getDataList();
            for ( Map<String, Object> dataMap : dataList ) {
                Tr newTr = (Tr) XmlUtils.unmarshallFromTemplate( dynamicTrXml, dataMap ); // 填充模板行数据
                trList.add( newTr );
            }
            trList.remove( 1 ); // 删除模板行的占位行
        }

        Map<String, String> mappings = new HashMap<>();
        mappings.put( "name", "峰" );
        mappings.put( "sex", "男" );
        mappings.put( "skill", "开发 Java" );
        documentPart.variableReplace( mappings );

        Docx4J.save( wordMLPackage, new File( outPath ) );
    }

    static List<Map<String, Object>> getDataList() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        IntStream.of( 1, 2, 3 )
                .forEach( i -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put( "number", "No." + i );
                    map.put( "name", "姓名-" + i );
                    map.put( "sex", i % 2 == 0 ? "女" : "男" );
                    map.put( "skill", "开发-Lv." + i );
                    dataList.add( map );
                } );
        return dataList;
    }

}
