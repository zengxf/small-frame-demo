package cn.zxf.jxls.demo.export;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import cn.zxf.jxls.demo.dto.UserDto;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * A1 备注：jx:area(lastCell="B2")
 * A2 备注：jx:each(items="users" var="user" lastCell="B2")
 * </pre>
 * 
 * <p>
 * Created by zxf on 2017-07-27
 */
@Slf4j
public class ExportUserTest {

    public static void main( String[] args ) throws FileNotFoundException, IOException {
	List<UserDto> users = new ArrayList<>();
	users.add( UserDto.builder().name( "zxf" ).age( 32 ).build() );
	users.add( UserDto.builder().name( "zxf-1" ).build() );

	log.info( "path: {}", ExportUserTest.class.getResource( "/user-template.xlsx" ).getFile() );
	log.info( "\n\t users: {}", users );

	try ( InputStream is = ExportUserTest.class.getResourceAsStream( "/user-template.xlsx" ) ) {
	    try ( OutputStream os = new FileOutputStream( "output/user-output.xlsx" ) ) {
		Context context = new Context();
		context.putVar( "users", users );
		JxlsHelper.getInstance().processTemplate( is, os, context );
	    }
	}

	log.info( "ok !!!" );
    }

}
