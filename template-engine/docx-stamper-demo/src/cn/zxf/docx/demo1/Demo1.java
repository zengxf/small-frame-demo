package cn.zxf.docx.demo1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.wickedsource.docxstamper.DocxStamper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo1 {
    public static void main( String[] args ) throws IOException {
        InputStream template = Demo1.class.getResourceAsStream( "demo1.docx" );
        OutputStream out = new FileOutputStream( "C:/Users/Administrator/Desktop/docx/demo1-res.docx" );
        DocxStamper<User> stamper = new DocxStamper<>();
        User context = new User( "zxf", 25 );
        stamper.stamp( template, context, out );
        out.close();
        log.info( "context: {}", context );
        log.info( "OK!!!" );
    }
}
