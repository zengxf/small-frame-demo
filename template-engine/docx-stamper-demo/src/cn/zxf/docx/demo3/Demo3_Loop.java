package cn.zxf.docx.demo3;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.wickedsource.docxstamper.DocxStamper;
import org.wickedsource.docxstamper.DocxStamperConfiguration;

import cn.zxf.docx.demo3.RecommendDto.CandidateDto;
import cn.zxf.docx.demo3.RecommendDto.EducationDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo3_Loop {

    public static void main( String[] args ) throws Exception {
        List<EducationDto> edus = IntStream.rangeClosed( 1, 10 )
                .mapToObj( i -> EducationDto.builder()
                        .degree( "本科" )
                        .major( "开发" )
                        .endDate( "2012-" + i )
                        .startDate( "2009-02" )
                        .school( "北大" )
                        .build() )
                .collect( Collectors.toList() );

        CandidateDto cdd = CandidateDto.builder()
                .name( "张三" )
                .age( 32 )
                .birthDate( "1992-10" )
                .career( "工作于 xxx \r\n工作于 xxx  \n工作于 xxx  \n 工作于 xxx  \n 工作于 xxx  \n 工作于 xxx  \n 工作于 xxx" )
                .degree( "本科" )
                .educations( edus )
                .gender( "男" )
                .marital( "未知" )
                .workRange( 3 )
                .city( "上海" )
                .build();

        String recommendReason = "相当可以 \r\n相当可以2  \n相当可以2";
        RecommendDto context = RecommendDto.builder()
                .positionName( "Java 开发" )
                .companyAlias( "Google 中国" )
                .recommendReason( recommendReason )
                .salaryDetail( "16K*14 \r\n没了  \n没了-" + Math.random() )
                .cdd( cdd )
                .build();

        long start = System.currentTimeMillis();
        out( context, "recommend_default.docx", "C:/Users/Administrator/Desktop/rec-test-1.docx" );
        System.out.println( "\n\n=====================\n\n" );
        log.info( "use time: {} ms", System.currentTimeMillis() - start );

        log.info( "context: {}", context );
        log.info( "OK!!!" );
    }

    static void out( RecommendDto context, String tempName, String outFile ) throws Exception {
        DocxStamperConfiguration config = new DocxStamperConfiguration();
        config.setLineBreakPlaceholder( "\n" );
        InputStream template = Demo3_Loop.class.getResourceAsStream( tempName );
        OutputStream out = new FileOutputStream( outFile );
        DocxStamper<RecommendDto> stamper = new DocxStamper<>( config );
        stamper.stamp( template, context, out );
        out.close();
    }

}
