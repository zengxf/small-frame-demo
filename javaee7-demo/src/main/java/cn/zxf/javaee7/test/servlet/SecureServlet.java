package cn.zxf.javaee7.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Arun Gupta
 */
@WebServlet( "/secure" )
@ServletSecurity( @HttpConstraint( rolesAllowed = "g1" ) )
public class SecureServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest( HttpServletRequest request, HttpServletResponse response, String method ) throws ServletException, IOException {
        response.setContentType( "text/html;charset=UTF-8" );

        PrintWriter out = response.getWriter();
        out.println( "<!DOCTYPE html>" );
        out.println( "<html>" );
        out.println( "<head>" );
        out.println( "<title>Servlet Security Annotated - Basic Auth with File-base Realm</title>" );
        out.println( "</head>" );
        out.println( "<body>" );
        out.println( "<h1>Basic Auth with File-base Realm (" + method + ")</h1>" );
        out.println( "<h2>Were you prompted for username/password ?</h2>" );
        out.println( "</body>" );
        out.println( "</html>" );
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        processRequest( request, response, "GET" );
    }

}
