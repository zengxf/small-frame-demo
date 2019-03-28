package cn.zxf.jxls.demo.export.merge;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;

import cn.zxf.jxls.demo.dto.GroupDto;
import cn.zxf.jxls.demo.dto.UserDto;
import lombok.extern.slf4j.Slf4j;

/**
 * 合并单元格
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
public class ExportMergeTest {

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

        log.info( "path: {}", ExportMergeTest.class.getResource( "/merge/group-merge-template.xlsx" )
                .getFile() );
        log.info( "\n\t groups: {}", groups );

        try ( InputStream is = ExportMergeTest.class.getResourceAsStream( "/merge/group-merge-template.xlsx" ) ) {
            try ( OutputStream os = new FileOutputStream( "output/group-merge-output.xlsx" ) ) {
                Context context = new Context();
                context.putVar( "groups", groups );

                JxlsHelper helper = JxlsHelper.getInstance();
                Transformer transformer = helper.createTransformer( is, os );
                AreaBuilder areaBuilder = helper.getAreaBuilder();
                areaBuilder.setTransformer( transformer );

                List<Area> xlsAreaList = areaBuilder.build();
                for ( Area xlsArea : xlsAreaList ) {
                    xlsArea.applyAt( new CellRef( xlsArea.getStartCellRef()
                            .getCellName() ), context );
                }
                transformer.write();
            }
        }

        log.info( "ok !!!" );
    }

}
