package test.jdkapi.awt;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class JScrollPaneDemo extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel            contentPane;
    private JScrollPane       scrollPane;
    private JTextArea         textArea;

    public JScrollPaneDemo() {
        contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        contentPane.setLayout( new BorderLayout( 0, 0 ) );

        this.setContentPane( contentPane );
        scrollPane = new JScrollPane();
        contentPane.add( scrollPane, BorderLayout.CENTER );
        textArea = new JTextArea();
        scrollPane.setViewportView( textArea );

        this.setTitle( "滚动面板使用" );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setBounds( 100, 100, 250, 200 );
        this.setVisible( true );
    }

    public static void main( String[] args ) {
        new JScrollPaneDemo();
    }
}
