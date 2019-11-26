package webtest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.inject.Module;

import io.bootique.Bootique;
import io.bootique.jersey.JerseyModule;

public class WebMain {

    public static void main( String[] args ) {
        Module module = binder -> JerseyModule.extend( binder )
                .addResource( HelloResource.class )
                .addResource( TestResource.class );

        // String path = WebMain.class.getResource( "/test.yml" )
        // .getPath();
        // String[] arr = { "--server", "--config=" + path };
        // Bootique.app( arr )

        // gradle build
        // java -jar build/libs/bootique-simple.jar -s -c build/resources/main/test.yml
        Bootique.app( args )
                .module( module )
                .autoLoadModules()
                .exec()
                .exit();
    }

    @Path( "/test" )
    public static class TestResource { // 要 public 修饰
        @GET
        public String test() {
            return "Test!";
        }
    }
}
