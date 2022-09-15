package im.server;

import im.server.server.ChatServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
@SpringBootApplication
@Slf4j
public class ServerApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ServerApplication.class, args);
        ChatServer nettyServer = context.getBean(ChatServer.class);
        nettyServer.run();
    }

}
