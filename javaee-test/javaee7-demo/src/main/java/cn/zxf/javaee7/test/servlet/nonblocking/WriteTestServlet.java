package cn.zxf.javaee7.test.servlet.nonblocking;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Arun Gupta
 */
@SuppressWarnings( "serial" )
@WebServlet( urlPatterns = { "/nonblock-write" } )
public class WriteTestServlet extends HttpServlet {

    protected void processRequest( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        response.setContentType( "text/html;charset=UTF-8" );
        try ( PrintWriter out = response.getWriter() ) {
            out.println( "<!DOCTYPE html>" );
            out.println( "<html>" );
            out.println( "<head>" );
            out.println( "<title>Writing Asynchronously</title>" );
            out.println( "</head>" );
            out.println( "<body>" );
            out.println( "<h1>Writing Asynchronously</h1>" );

            AsyncContext context = request.startAsync();
            ServletOutputStream output = response.getOutputStream();
            output.setWriteListener( new MyWriteListener( output, context ) );

            out.println( "</body>" );
            out.println( "</html>" );
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
