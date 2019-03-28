package cn.zxf.javaee7.test.servlet;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( urlPatterns = "/my-async", asyncSupported = true )
public class MyAsyncServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    ExecutorService           executor         = Executors.newFixedThreadPool( 4 );

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        processRequest( request, response );
    }

    @Override
    public String getServletInfo() {
        return "async test";
    }

    protected void processRequest( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        AsyncContext ac = request.startAsync();

        ac.addListener( new AsyncListener() {
            @Override
            public void onComplete( AsyncEvent event ) throws IOException {
                System.out.println( "onComplete" );
                event.getSuppliedResponse().getWriter().println( "onComplete !!!" );
            }

            @Override
            public void onTimeout( AsyncEvent event ) throws IOException {
                event.getSuppliedResponse().getWriter().println( "onTimeout" );
                event.getAsyncContext().complete();
            }

            @Override
            public void onError( AsyncEvent event ) throws IOException {
                event.getSuppliedResponse().getWriter().println( "onError" );
            }

            @Override
            public void onStartAsync( AsyncEvent event ) throws IOException {
                System.out.println( "start async" );
                event.getSuppliedResponse().getWriter().println( "onStartAsync !!!" );
            }
        } );
        executor.submit( new MyAsyncService( ac ) );
    }

    class MyAsyncService implements Runnable {

        AsyncContext ac;

        public MyAsyncService( AsyncContext ac ) {
            this.ac = ac;
        }

        @Override
        public void run() {
            try {
                System.out.println( "run" );
                ac.getResponse().getWriter().println( "Running inside MyAsyncService !!!" );
            } catch ( IOException e ) {
                throw new IllegalStateException( e );
            }
            ac.complete();
        }
    }

}
