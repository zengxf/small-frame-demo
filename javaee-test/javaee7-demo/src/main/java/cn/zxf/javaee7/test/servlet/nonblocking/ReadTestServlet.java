package cn.zxf.javaee7.test.servlet.nonblocking;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Arun Gupta
 */
@SuppressWarnings( "serial" )
@WebServlet( urlPatterns = { "/nonblock-read" }, asyncSupported = true )
public class ReadTestServlet extends HttpServlet {

    protected void processRequest( HttpServletRequest request, HttpServletResponse response ) //
            throws ServletException, IOException {
        response.setContentType( "text/html;charset=UTF-8" );
        try ( PrintWriter output = response.getWriter() ) {
            output.println( "<html>" );
            output.println( "<head>" );
            output.println( "<title>Reading asynchronously</title>" );
            output.println( "</head>" );
            output.println( "<body>" );
            output.println( "<h1>Reading asynchronously</h1>" );

            AsyncContext context = request.startAsync();
            ServletInputStream input = request.getInputStream();
            input.setReadListener( new MyReadListener( input, context ) );

            output.println( "</body>" );
            output.println( "</html>" );
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
    }
}
