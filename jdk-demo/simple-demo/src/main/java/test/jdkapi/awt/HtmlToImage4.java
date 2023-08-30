package test.jdkapi.awt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;

import javax.imageio.ImageIO;

public class HtmlToImage4 {

    static String CSSCOLOR = "#bf8f79";
    static float  ALPHA    = 0.4f;

    static void generateOutput() throws Exception {
        URI contentUri = HtmlToImage4.class.getResource( "test.html" )
                .toURI();
        HtmlToImageUtil.log( "contentUri: %s", contentUri );
        String contentHtml = HtmlToImageUtil.toString( contentUri, "UTF-8" );

        URI templateUri = HtmlToImage4.class.getResource( "morning-template.html" )
                .toURI();
        String templateHtml = HtmlToImageUtil.toString( templateUri, "UTF-8" );

        String html = templateHtml.replace( "#content#", contentHtml );
        String headImagePath = HtmlToImageUtil.class.getResource( "morning-head_bg.png" )
                .toURI()
                .toString();
        String paperImagePath = HtmlToImageUtil.class.getResource( "morning-paper_bg.png" )
                .toURI()
                .toString();

        HtmlToImageUtil.log( "html: \n%s", html );

        String saveFile;
        if ( System.getProperty( "os.name" )
                .startsWith( "Windows" ) ) {
            saveFile = "C:\\Users\\Administrator\\Desktop/test.png";
        } else {
            saveFile = HtmlToImage4.class.getResource( "" )
                    .getPath() + "test-linux.png";
        }
        HtmlToImageUtil.log( "saveFile: %s", saveFile );

        BufferedImage image = HtmlToImageUtil.toImage( html, headImagePath, paperImagePath );
        ImageIO.write( image, "png", new File( saveFile ) );
        HtmlToImageUtil.log( "OK !!!" );
    }

    public static void main( String[] args ) {
        try {
            generateOutput();
            HtmlToImageUtil.log( "OK" );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}
