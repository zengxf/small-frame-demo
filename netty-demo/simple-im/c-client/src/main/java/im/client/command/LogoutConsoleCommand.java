package im.client.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
@Service
@Slf4j
public class LogoutConsoleCommand implements BaseCommand {

    public static final String KEY = "10";


    @Override
    public void exec(Scanner scanner) {
        log.info("退出命令执行成功");
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTip() {
        return "退出";
    }

}
