package cn.zxf.javaee7.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.WebConnection;

@SuppressWarnings( "serial" )
@WebServlet( urlPatterns = { "/upgrade" } )
public class UpgradeServlet extends HttpServlet {

    protected void processRequest( HttpServletRequest request, HttpServletResponse response )//
            throws ServletException, IOException {
        response.setContentType( "text/html;charset=UTF-8" );
        try ( PrintWriter out = response.getWriter() ) {
            out.println( "<html>" );
            out.println( "<head>" );
            out.println( "<title>Servlet UpgradeServlet</title>" );
            out.println( "</head>" );
            out.println( "<body>" );
            out.println( "<h1>Servlet UpgradeServlet at " + request.getContextPath() + "</h1>" );
            if ( "echo".equals( request.getHeader( "Upgrade" ) ) ) {
                response.setStatus( HttpServletResponse.SC_SWITCHING_PROTOCOLS );
                response.setHeader( "Connection", "Upgrade" );
                response.setHeader( "Upgrade", "echo" );

                request.upgrade( MyProtocolHandler.class );
                System.out.println( "Request upgraded to MyProtocolHandler" );
            }
            out.println( "</body>" );
            out.println( "</html>" );
        }
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        processRequest( request, response );
    }

    /**
     * 处理类
     * 
     * <p>
     * Created by zengxf on 2017-10-17
     */
    public static class MyProtocolHandler implements HttpUpgradeHandler {

        @Override
        public void init( WebConnection wc ) {
            System.out.println( "MyProtocolHandler init ..." );
            try ( ServletInputStream input = wc.getInputStream(); ServletOutputStream output = wc.getOutputStream(); ) {
                System.out.println( "write msg" );
                output.write( "test by handler".getBytes() );
                int len = -1;
                byte b[] = new byte[1024];
                while ( ( len = input.read( b ) ) != -1 ) {
                    String data = new String( b, 0, len );
                    System.out.println( "--> " + data );
                }
            } catch ( IOException ex ) {
                ex.printStackTrace();
            }
        }

        @Override
        public void destroy() {
            System.out.println( "MyProtocolHandler destroy" );
        }
    }

}