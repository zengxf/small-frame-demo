package webtest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/")
public class HelloResource {

    @Inject
    HelloService service;

    @GET
    public String hello() {
        System.out.println("service ========> " + service);
        service.test();
        return "Hello, world!";
    }

}
