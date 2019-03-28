package cn.zxf.javaee7.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Arun Gupta
 */
@WebServlet( urlPatterns = { "/login" } )
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        response.setContentType( "text/html;charset=UTF-8" );
        String user = request.getParameter( "user" );
        String password = request.getParameter( "password" );

        if ( user != null && password != null ) {
            request.login( user, password );
        }

        userDetails( response.getWriter(), request );
    }

    private void userDetails( PrintWriter out, HttpServletRequest request ) {
        out.println( "isUserInRole?" + request.isUserInRole( "g1" ) );
        out.println( "getRemoteUser?" + request.getRemoteUser() );
        out.println( "getUserPrincipal?" + request.getUserPrincipal() );
        out.println( "getAuthType?" + request.getAuthType() );
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        processRequest( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        processRequest( request, response );
    }

}
