package im.client.command;

import java.util.Scanner;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
public interface BaseCommand {

    // 命令 key
    String getKey();

    // 命令提示信息
    String getTip();

    // 从控制台提取业务数据
    void exec(Scanner scanner);

}
