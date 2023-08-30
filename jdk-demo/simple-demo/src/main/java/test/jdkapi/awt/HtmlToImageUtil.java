package test.jdkapi.awt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * HTML 转换成图片的帮助类
 * <p>
 * Created by zengxf on 2017/5/11.
 */
public class HtmlToImageUtil {

    public final static String CONTENT_SIGN = "#content#";
    public final static Color  BG_COLOR     = new Color( 255, 255, 255 );
    public final static int    IMAGE_WIDTH  = 750;

    /**
     * 转换成图片
     *
     * @param html
     * @param headImageUrl
     * @param paperImageUrl
     * @return
     * @throws IOException
     */
    public static BufferedImage toImage( String html, String headImageUrl, String paperImageUrl ) throws IOException {
        URL headFile = new URL( headImageUrl );
        Image headImage = ImageIO.read( headFile );
        int headWidth = headImage.getWidth( null );
        int headHeight = headImage.getHeight( null );
        log( "headWidth: %s, headHeight: %s", headWidth, headHeight );

        JEditorPane ed = new JEditorPane();
        ed.setContentType( "text/html" );
        ed.setText( html );

        URL paperFile = new URL( paperImageUrl );
        Image paperImage = ImageIO.read( paperFile );
        int paperWidth = paperImage.getWidth( null );
        int paperHeight = paperImage.getHeight( null );
        log( "paperWidth: %s, paperHeight: %s", paperWidth, paperHeight );

        Dimension prefSize = ed.getPreferredSize();
        int prefWidth = prefSize.width;
        int prefHeight = prefSize.height;
        prefHeight = prefHeight < paperHeight + 20 ? paperHeight + 20 : prefHeight;
        log( "prefWidth: %s, prefHeight: %s", prefWidth, prefHeight );

        BufferedImage image = new BufferedImage( IMAGE_WIDTH, prefHeight + headHeight, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g2d = image.createGraphics();
        int width = image.getWidth( null );
        int height = image.getHeight( null );
        log( "imageWidth: %s, imageHeight: %s", width, height );

        { // 设置头
            g2d.setBackground( BG_COLOR ); // 设置背景色
            g2d.clearRect( 0, 0, width, height ); // 用背景色进行填充来清除指定的矩形

            int x = ( width - headWidth ) / 2;
            int y = 0;
            g2d.drawImage( headImage, x, y, headWidth, headHeight, null );
        }

        SwingUtilities.paintComponent( g2d, ed, new JPanel(), 0, headHeight, prefWidth, prefHeight );

        { // 设置水印
            int x = ( width - paperWidth ) / 2;
            int startY = headHeight + 24 * 2 + 10;
            int blank = height - headHeight;
            int interval = 0;
            int compute = blank / ( interval + paperHeight );
            int actual = Math.max( 1, compute );
            for ( int i = 0; i <= actual; i++ ) {
                int y = startY + ( headHeight + interval ) * i;
                if ( y + headHeight < height ) {
                    g2d.drawImage( paperImage, x, y, paperWidth, paperHeight, null );
                    log( "paper y: %s", y );
                }
            }
        }

        g2d.dispose(); // 结束

        return image;
    }

    // -----------------
    // -----------------
    // -----------------

    public static void log( String fmt, Object... objects ) {
        System.out.println( String.format( fmt, objects ) );
    }

    public static String toString( URI uri, String encoding ) {
        try {
            InputStream is = uri.toURL()
                    .openStream();
            int len = is.available();
            log( "len: %s", len );
            byte[] bs = new byte[len];
            is.read( bs );
            return new String( bs, encoding );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return "";

    }

}
