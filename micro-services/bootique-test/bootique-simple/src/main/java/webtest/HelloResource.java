package webtest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.inject.Inject;

@Path( "/" )
public class HelloResource {
    @Inject
    HelloService service;

    @GET
    public String hello() {
        System.out.println( "service========" + service );
        service.test();
        return "Hello, world!";
    }
}
