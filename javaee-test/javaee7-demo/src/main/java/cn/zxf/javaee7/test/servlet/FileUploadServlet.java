package cn.zxf.javaee7.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * file input 必须指定 name
 */
@SuppressWarnings( "serial" )
@WebServlet( urlPatterns = { "/file-upload" } )
// @MultipartConfig( location = "/tmp" )
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    protected void processRequest( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        response.setContentType( "text/html;charset=UTF-8" );

        String path = request.getServletContext().getAttribute( ServletContext.TEMPDIR ).toString();
        System.out.println( "temp path: " + path );

        try ( PrintWriter out = response.getWriter() ) {
            out.println( "<!DOCTYPE html>" );
            out.println( "<html>" );
            out.println( "<head>" );
            out.println( "<title>File Upload Servlet</title>" );
            out.println( "</head>" );
            out.println( "<body>" );
            out.println( "<h1>File Upload Servlet</h1>" );
            out.println( "Receiving the uploaded file ...<br>" );
            out.println( "Received " + request.getParts().size() + " parts ...<br>" );
            String fileName = "";
            for ( Part part : request.getParts() ) {
                fileName = part.getSubmittedFileName();
                out.println( "... writing 《" + fileName + "》 part<br>" );
                part.write( fileName );
                out.println( "... written<br>" );
            }
            out.println( "... uploaded to: /tmp/《" + fileName + "》" );
            out.println( "</body>" );
            out.println( "</html>" );
        }
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
