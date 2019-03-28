package cn.zxf.jxls.demo.export.api;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jxls.area.XlsArea;
import org.jxls.command.EachCommand;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.util.TransformerFactory;

import cn.zxf.jxls.demo.dto.GroupDto;
import cn.zxf.jxls.demo.dto.UserDto;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <p>
 * Created by zxf on 2017-07-27
 */
@Slf4j
public class ExportGroupJavaApiTest {

    public static void main( String[] args ) throws FileNotFoundException, IOException {
        List<UserDto> users = new ArrayList<>();
        users.add( UserDto.builder().name( "zxf" ).age( 32 ).build() );
        users.add( UserDto.builder().name( "zxf-1" ).build() );
        List<GroupDto> groups = new ArrayList<>();
        groups.add( GroupDto.builder().id( "group-001" ).name( "zxf-屠龙组" ).users( users ).build() );
        groups.add( GroupDto.builder().id( "group-002" ).name( "zxf-屠龙组2" ).users( users ).build() );

        URL url = ExportGroupJavaApiTest.class.getResource( "/api/group-java-api-template.xlsx" );
        log.info( "path: {}", url.getFile() );
        log.info( "\n\t groups: {}", groups );

        try ( InputStream is = url.openStream() ) {
            try ( OutputStream os = new FileOutputStream( "output/group-java-api-output.xlsx" ) ) {
                Transformer transformer = TransformerFactory.createTransformer( is, os );
                XlsArea xlsArea = new XlsArea( "Group-List!A1:D2", transformer );

                XlsArea groupArea = new XlsArea( "Group-List!A2:D2", transformer );
                EachCommand groupCmd = new EachCommand( "group", "groups", groupArea );
                xlsArea.addCommand( "A2:D2", groupCmd );

                XlsArea userArea = new XlsArea( "Group-List!C2:D2", transformer );
                EachCommand userCmd = new EachCommand( "user", "group.users", userArea );
                groupArea.addCommand( "C2:D2", userCmd );

                Context context = new Context();
                context.putVar( "groups", groups );
                xlsArea.applyAt( new CellRef( "Group-List!A1" ), context );
                transformer.write();
            }
        }

        log.info( "ok !!!" );
    }

}
