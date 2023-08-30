package test.jdkapi.awt.watermarks;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

/**
 * 用文字生成水印
 * <p>
 * Created by zengxf on 2019-04-16
 */
public class GenerateWatermarkUseText {

    public static void main( String[] args ) throws IOException {
        String targetPath = "C:\\Users\\Administrator\\Desktop\\aa";
        String outPath = targetPath + "\\文字水印";
        String[] texts = { "昵称：雪 花", "QQ：1549070207", "微信：A2968769411" };
        mkdir( outPath );

        Files.find( Paths.get( targetPath ), 1, ( p, a ) -> {
            boolean endsWith = p.getFileName()
                    .toString()
                    .endsWith( ".jpg" );
            return endsWith;
        } )
                .forEach( jpg -> {
                    String fileName = jpg.getFileName()
                            .toString();

                    System.out.println( jpg.toString() + "\t" + fileName );

                    File srcImgFile = jpg.toFile();
                    File outImageFile = Paths.get( outPath, fileName )
                            .toFile();
                    createWaterMarkByText( srcImgFile, texts, outImageFile );
                } );

    }

    static void createWaterMarkByText( File srcImgFile, String[] texts, File outputImageFile ) {
        float alpha = 0.2f;
        double angdeg = 350;

        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read( srcImgFile );
            int width = srcImg.getWidth( null );
            int height = srcImg.getHeight( null );

            BufferedImage buffImg = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );

            Graphics2D graphics = buffImg.createGraphics();
            graphics.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
            graphics.drawImage( srcImg.getScaledInstance( width, height, Image.SCALE_SMOOTH ), 0, 0, null );
            graphics.rotate( Math.toRadians( angdeg ), width / 2, height / 2 ); // 旋转
            graphics.setColor( Color.pink ); // 颜色
            graphics.setFont( new Font( "宋体", Font.BOLD, 60 ) ); // 字体
            graphics.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_ATOP, alpha ) ); // 透明度

            // 写字
            int i = 0;
            for ( int w = 0; w < width; w += 600 ) {
                for ( int h = 200; h < height; h += 240 ) {
                    String text = texts[i++ % texts.length];
                    graphics.drawString( text, w, h );
                }
            }

            graphics.dispose(); // 释放资源
            os = new FileOutputStream( outputImageFile );
            ImageIO.write( buffImg, "JPG", os ); // 生成图片
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            try {
                if ( null != os )
                    os.close();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    static void mkdir( String path ) {
        try {
            Path filePath = Paths.get( path );
            if ( Files.exists( filePath ) && Files.isDirectory( filePath ) )
                return;
            Files.createDirectory( filePath );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

}
