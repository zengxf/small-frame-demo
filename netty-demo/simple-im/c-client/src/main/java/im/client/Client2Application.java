package im.client;

import im.client.client.CommandController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
@SpringBootApplication
@Slf4j
public class Client2Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Client2Application.class, args);
        CommandController commandClient = context.getBean(CommandController.class);
        commandClient.initCommandMap();
        commandClient.commandThreadRunning();
    }

}
