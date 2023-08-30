package test.jdkapi.awt.watermarks;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class WatermarkGUI {
    JFrame              mainFrame        = new JFrame( "自动化生成水印" );
    Container           mainPane         = mainFrame.getContentPane();
    JButton             srcChooserButton = new JButton( "选择-文件" );
    JButton             imgChooserButton = new JButton( "选择-水印" );
    JTextField          srcPathText      = new JTextField();
    JTextField          imgPathText      = new JTextField();
    JTextArea           msgArea          = new JTextArea();
    JButton             okButton         = new JButton( "确定" );
    JButton             cancelButton     = new JButton( "取消" );

    static final String DEF_PATH         = System.getProperty( "def-path",                     //
            String.format( "C:\\Users\\%s\\Desktop\\aa", System.getProperty( "user.name" ) ) );

    public WatermarkGUI() {
        mainFrame.setSize( 900, 800 );
        mainPane.setLayout( new BorderLayout() );
        this.initFrame();
        this.setupAttribute();
        this.setupEventListener();
        mainFrame.setVisible( true );
    }

    void initFrame() {
        // 顶部
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout( new FlowLayout() );
        titlePanel.add( new JLabel( "生成水印-曾献峰-制作" ) );
        mainPane.add( titlePanel, BorderLayout.NORTH );

        // 中部表单
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout( null );
        JLabel a1 = new JLabel( "原图文件夹：" );
        a1.setBounds( 50, 20, 150, 20 );
        JLabel a2 = new JLabel( "水印图片路径：" );
        a2.setBounds( 50, 60, 150, 20 );
        fieldPanel.add( a1 );
        fieldPanel.add( a2 );

        srcPathText.setBounds( 180, 20, 550, 20 );
        srcChooserButton.setBounds( 750, 20, 100, 20 );
        imgPathText.setBounds( 180, 60, 550, 20 );
        imgChooserButton.setBounds( 750, 60, 100, 20 );
        msgArea.setBounds( 50, 100, 800, 580 );
        fieldPanel.add( srcPathText );
        fieldPanel.add( srcChooserButton );
        fieldPanel.add( imgPathText );
        fieldPanel.add( imgChooserButton );
        fieldPanel.add( msgArea );
        mainPane.add( fieldPanel, BorderLayout.CENTER );

        // 底部按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout( new FlowLayout() );
        buttonPanel.add( okButton );
        buttonPanel.add( cancelButton );
        mainPane.add( buttonPanel, BorderLayout.SOUTH );
    }

    void setupAttribute() {
        msgArea.setEditable( false );
        msgArea.append( "消息显示：\n" );
    }

    void setupEventListener() {
        mainFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                WatermarkGUI.this.logMessage( "窗口-关闭" );
                System.exit( 0 );
            }
        } );
        srcChooserButton.addActionListener( this::imgChooserButtonHandle );
        imgChooserButton.addActionListener( this::imgChooserButtonHandle );
        okButton.addActionListener( this::okButtonHandle );
        cancelButton.addActionListener( e -> {
            this.logMessage( "取消-关闭" );
            System.exit( 0 );
        } );
    }

    void srcChooserButtonHandle( ActionEvent e ) {
        JFileChooser chooser = new JFileChooser( DEF_PATH );
        chooser.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
        chooser.setFileFilter( new FileFilter() {
            @Override
            public boolean accept( File file ) {
                String name = file.getName()
                        .toLowerCase();
                return file.isDirectory() || name.endsWith( ".jpg" );
            }

            @Override
            public String getDescription() {
                return "*.jpg 或 文件夹";
            }
        } );
        chooser.showDialog( mainFrame, "选择源图文件或文件夹" );
        File file = chooser.getSelectedFile();
        if ( file == null )
            return;
        srcPathText.setText( file.getAbsolutePath() );
    }

    void imgChooserButtonHandle( ActionEvent e ) {
        JFileChooser chooser = new JFileChooser( DEF_PATH );
        chooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
        chooser.setFileFilter( new FileFilter() {
            @Override
            public boolean accept( File file ) {
                return file.getName()
                        .toLowerCase()
                        .endsWith( ".png" );
            }

            @Override
            public String getDescription() {
                return "*.png";
            }
        } );
        chooser.showDialog( mainFrame, "选择水印图" );
        File file = chooser.getSelectedFile();
        if ( file == null )
            return;
        imgPathText.setText( file.getAbsolutePath() );
    }

    void okButtonHandle( ActionEvent e ) {
        String srcPath = srcPathText.getText();
        if ( srcPath.isBlank() ) {
            this.logMessage( "请输入-原图文件夹" );
            return;
        }
        String imgPath = imgPathText.getText();
        if ( imgPath.isBlank() ) {
            this.logMessage( "请输入-水印图片路径" );
            return;
        }
        if ( !Files.exists( Paths.get( srcPath ) ) ) {
            this.logMessage( "原图文件夹：[%s] 不存在", srcPath );
            return;
        }
        if ( !Files.isDirectory( Paths.get( srcPath ) ) ) {
            this.logMessage( "原图文件夹：[%s] 不是文件夹", srcPath );
            return;
        }
        if ( !Files.exists( Paths.get( imgPath ) ) ) {
            this.logMessage( "水印图片：[%s] 不存在", imgPath );
            return;
        }
        this.logMessage( "原图文件夹：[%s]", srcPath );
        this.logMessage( "水印图片路径：[%s]", imgPath );
        this.logMessage( "确定-开始操作" );
    }

    void logMessage( String fmt, Object... args ) {
        Object[] param = new Object[args.length + 1];
        param[0] = System.currentTimeMillis();
        System.arraycopy( args, 0, param, 1, args.length );
        String msg = String.format( "  [%tT] " + fmt, param );

        System.out.println( msg );
        msgArea.append( msg );
        msgArea.append( "\n" );
    }

    public static void main( String[] args ) {
        new WatermarkGUI();
    }

}
