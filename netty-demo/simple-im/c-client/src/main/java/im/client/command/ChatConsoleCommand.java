package im.client.command;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
@Service
@Getter
public class ChatConsoleCommand implements BaseCommand {

    public static final String KEY = "2";

    private String toUserId;
    private String message;


    @Override
    public void exec(Scanner scanner) {
        System.out.print("请输入聊天的消息(id:message)：");
        String[] info;
        while (true) {
            String input = scanner.next();
            info = input.split(":");
            if (info.length != 2) {
                System.out.println("请输入聊天的消息(id:message)：");
            } else {
                break;
            }
        }
        toUserId = info[0];
        message = info[1];
    }


    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTip() {
        return "聊天";
    }

}