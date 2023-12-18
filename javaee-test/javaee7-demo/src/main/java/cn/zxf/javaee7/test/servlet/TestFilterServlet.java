package cn.zxf.javaee7.test.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Kuba Marchwicki
 */
@SuppressWarnings( "serial" )
@WebServlet( urlPatterns = { "/test-filter", "/filtered/test-filter" } )
public class TestFilterServlet extends HttpServlet {

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        // response.setContentType( "text/html;charset=UTF-8" );
        try ( PrintWriter out = response.getWriter() ) {
            out.println( "serverlt-bar" );
        }
    }
}
