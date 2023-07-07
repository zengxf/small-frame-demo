package webtest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import io.bootique.Bootique;
import io.bootique.di.BQModule;
import io.bootique.jersey.JerseyModule;

import java.util.Map;

public class WebMain {

    public static void main(String[] args) {
        String path = WebMain.class.getResource("/test.yml").getPath();
        String[] arr = {"--server", "--config=" + path};

        BQModule module = binder -> JerseyModule.extend(binder)
                .addResource(TestResource.class)
                .addResource(HelloResource.class);
        Bootique
                .app(arr)
                .module(module)
                .autoLoadModules()
                .exec()
                .exit();
    }

    @Path("/test")
    public static class TestResource { // 要 public 修饰
        @GET // 只能是字符串返回
        public Map<String, Object> test() {
            return Map.of(
                    "k-i", 88,
                    "k-s", "OK"
            );
        }
    }

}
