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
@Deprecated // 合并没源码
public class TableRowMergeTest {

    public static void main( String[] args ) throws JAXBException, Docx4JException, IOException {
        URL url = TableRowMergeTest.class.getResource( "/表格行合并_template.docx" );
        log.info( "url: {}", url );
        InputStream templateIs = url.openStream();
        String outPath = "C:/Users/Administrator/Desktop/表格行合并.docx";
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load( templateIs );
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        // 找到模板中的表格对象
        ClassFinder find = new ClassFinder( Tbl.class );
        new TraversalUtil( documentPart.getContent(), find );
        Tbl table = (Tbl) find.results.get( 0 ); // 第一个表格
        fillTableDataAndMerge( table ); // 填充数据和合并单元格

        Docx4J.save( wordMLPackage, new File( outPath ) );
    }

    // 根据表格模板找到表格,并填充数据

    private static void fillTableDataAndMerge( Tbl table ) throws JAXBException {
        List<Map<String, Object>> dataList = getDataList();
        List<Object> trList = table.getContent();
        Tr dynamicTr = (Tr) trList.get( 2 ); // 第三行约定为模板
        String dynamicTrXml = XmlUtils.marshaltoString( dynamicTr ); // 获取模板行的 xml 数据
        log.info( "dynamic-tr-xml: {}", dynamicTrXml );
        for ( Map<String, Object> dataMap : dataList ) {
            Tr newTr = (Tr) XmlUtils.unmarshallFromTemplate( dynamicTrXml, dataMap ); // 填充模板行数据
            trList.add( newTr );
        }
        trList.remove( 2 ); // 删除模板行的占位行

        // 合并单元格，没实现
    }

    static List<Map<String, Object>> getDataList() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        IntStream.of( 3, 2, 1 )
                .forEach( i -> { // 公司
                    IntStream.rangeClosed( 1, i )
                            .forEach( j -> { // 部门
                                IntStream.rangeClosed( 1, j )
                                        .forEach( k -> { // 人
                                            Map<String, Object> map = new HashMap<>();
                                            map.put( "companyName", "公司 No." + i );
                                            map.put( "deptName", "部门 No." + i + "-" + j );
                                            map.put( "name", "姓名-" + i + "-" + j + "-" + k );
                                            map.put( "sex", k % 2 == 0 ? "女" : "男" );
                                            map.put( "remark", "开发-Lv." + k );
                                            dataList.add( map );
                                        } );
                            } );
                } );
        return dataList;
    }

}
