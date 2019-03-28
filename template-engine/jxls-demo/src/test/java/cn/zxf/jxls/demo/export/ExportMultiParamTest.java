package cn.zxf.jxls.demo.export;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import cn.zxf.jxls.demo.dto.UserDto;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * A1 备注：jx:area(lastCell="B3")
 * A3 备注：jx:each(items="users" var="user" lastCell="B3")
 * </pre>
 * 
 * <p>
 * Created by zxf on 2017-07-27
 */
@Slf4j
public class ExportMultiParamTest {

    public static void main( String[] args ) throws FileNotFoundException, IOException {
        List<UserDto> users = new ArrayList<>();
        users.add( UserDto.builder().name( "zxf" ).age( 32 ).build() );
        users.add( UserDto.builder().name( "zxf-1" ).build() );

        URL resource = ExportMultiParamTest.class.getResource( "/multi-param-template.xlsx" );
        log.info( "path: {}", resource.getFile() );
        log.info( "\n\t users: {}", users );

        try ( InputStream is = resource.openStream() ) {
            try ( OutputStream os = new FileOutputStream( "output/multi-param-output.xlsx" ) ) {
                Context context = new Context();
                context.putVar( "date", LocalDate.now().toString() );
                context.putVar( "users", users );
                JxlsHelper.getInstance().processTemplate( is, os, context );
            }
        }

        log.info( "ok !!!" );
    }

}
