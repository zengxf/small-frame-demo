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

import cn.zxf.jxls.demo.dto.GroupDto;
import cn.zxf.jxls.demo.dto.UserDto;
import lombok.extern.slf4j.Slf4j;

/**
 * 并不能合并单元格
 * 
 * <pre>
 * A1 备注：jx:area(lastCell="D2")
 * A2 备注：jx:each(items="groups" var="group" lastCell="B2")
 * C2 备注：jx:each(items="group.users" var="user" lastCell="D2")
 * </pre>
 * 
 * <p>
 * Created by zxf on 2017-07-27
 */
@Slf4j
public class ExportGroupTest {

    public static void main( String[] args ) throws FileNotFoundException, IOException {
        List<UserDto> users = new ArrayList<>();
        users.add( UserDto.builder()
                .name( "zxf" )
                .age( 32 )
                .build() );
        users.add( UserDto.builder()
                .name( "zxf-1" )
                .build() );
        List<GroupDto> groups = new ArrayList<>();
        groups.add( GroupDto.builder()
                .id( "group-001" )
                .name( "zxf-屠龙组" )
                .users( users )
                .build() );
        groups.add( GroupDto.builder()
                .id( "group-002" )
                .name( "zxf-屠龙组2" )
                .users( users )
                .build() );

        log.info( "path: {}", ExportGroupTest.class.getResource( "/group-template.xlsx" )
                .getFile() );
        log.info( "\n\t groups: {}", groups );

        try ( InputStream is = ExportGroupTest.class.getResourceAsStream( "/group-template.xlsx" ) ) {
            try ( OutputStream os = new FileOutputStream( "output/group-output.xlsx" ) ) {
                Context context = new Context();
                context.putVar( "groups", groups );
                JxlsHelper.getInstance()
                        .processTemplate( is, os, context );
            }
        }

        log.info( "ok !!!" );
    }

}
