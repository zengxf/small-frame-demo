package cn.zxf.javaee7.test.filter;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

@WebFilter( filterName = "FooBarFilter", urlPatterns = { "/filtered/*" } )
public class FooBarFilter implements Filter {

    private void doBeforeProcessing( ServletRequest request, ServletResponse response ) throws IOException, ServletException {
        try ( PrintWriter out = response.getWriter() ) {
            out.println( "filter-before-foo--》<br>" );
            out.flush();
        }
    }

    private void doAfterProcessing( ServletRequest request, ServletResponse response ) throws IOException, ServletException {
        try ( PrintWriter out = response.getWriter() ) {
            out.println( "<br>--filter-after-bar--》" );
            out.flush();
        }
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {
        response.setContentType( "text/html;charset=UTF-8" );
        PrintWriter out = response.getWriter(); // 不先设置，会在此方法里面设置
        CharResponseWrapper wrappedResponse = new CharResponseWrapper( (HttpServletResponse) response );

        doBeforeProcessing( request, wrappedResponse );
        chain.doFilter( request, wrappedResponse );
        doAfterProcessing( request, wrappedResponse );
        out.println( wrappedResponse.getContent() );
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init( FilterConfig filterConfig ) {
    }

    /**
     * 响应包装器
     * 
     * <p>
     * Created by zengxf on 2017-10-17
     */
    public static class CharResponseWrapper extends HttpServletResponseWrapper {
        private CharArrayWriter output;

        public String getContent() {
            return output.toString();
        }

        public CharResponseWrapper( HttpServletResponse response ) {
            super( response );
            output = new CharArrayWriter();
        }

        public PrintWriter getWriter() {
            return new PrintWriter( output );
        }
    }

}
