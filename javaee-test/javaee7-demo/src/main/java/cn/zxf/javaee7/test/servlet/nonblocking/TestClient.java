package cn.zxf.javaee7.test.servlet.nonblocking;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Arun Gupta
 */
@SuppressWarnings( "serial" )
@WebServlet( urlPatterns = { "/nonblock-client" } )
public class TestClient extends HttpServlet {

    protected void processRequest( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        response.setContentType( "text/html;charset=UTF-8" );
        try ( PrintWriter out = response.getWriter() ) {
            out.println( "<html>" );
            out.println( "<head>" );
            out.println( "<title>Invoke the servlet clients</title>" );
            out.println( "</head>" );
            out.println( "<body>" );
            out.println( "<h1>Invoke the servlet clients</h1>" );

            String path = "http://" + request.getServerName() + ":" + request.getServerPort() //
                    + request.getContextPath() + "/nonblock-read";
            out.println( "Invoking the endpoint: " + path + "<br>" );
            out.flush();
            URL url = new URL( path );
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setChunkedStreamingMode( 2 );
            conn.setDoOutput( true );
            conn.connect();
            try ( BufferedWriter output = new BufferedWriter( new OutputStreamWriter( conn.getOutputStream() ) ) ) {
                out.println( "Sending data ..." + "<br>" );
                out.flush();
                output.write( "Hello" );
                output.flush();
                out.println( "Sleeping ..." + "<br>" );
                out.flush();
                Thread.sleep( 5000 );
                out.println( "Sending more data ..." + "<br>" );
                out.flush();
                output.write( "World" );
                output.flush();
                output.close();
            }
            out.println( "<br><br>Check server.log for output" );
            out.println( "</body>" );
            out.println( "</html>" );
        } catch ( InterruptedException | IOException ex ) {
            Logger.getLogger( ReadTestServlet.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        processRequest( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        processRequest( request, response );
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
