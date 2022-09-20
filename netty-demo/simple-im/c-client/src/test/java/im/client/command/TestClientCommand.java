package im.client.command;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/20.
 */
@Slf4j
public class TestClientCommand {

    static Map<String, BaseCommand> commandMap;
    static ClientCommandMenu clientCommandMenu = new ClientCommandMenu();

    public static void main(String[] args) {
        // testLoginCommand();
        // testClientCommandMenu();
        testCommandController();
    }

    static void testLoginCommand() {
        LoginConsoleCommand logCmd = new LoginConsoleCommand();
        // 处理命令
        while (true) {
            Scanner scanner = new Scanner(System.in);
            logCmd.exec(scanner);
            log.info("本次输入的 username 为：" + logCmd.getUserName());
            log.info("本次输入的 password 为：" + logCmd.getPassword());
        }
    }

    static void testClientCommandMenu() {
        ClientCommandMenu commandMenu = new ClientCommandMenu();
        commandMenu.setAllCommandsShow("[menu] 0 -> show 所有命令  ||  1 -> 登录  ||  ...");
        Scanner scanner = new Scanner(System.in);
        // 处理命令
        while (true) {
            commandMenu.exec(scanner);
            log.info("本次输入的 菜单 为：" + commandMenu.getCommandInput());
        }
    }

    static void testCommandController() {
        initCommandMap();
        while (true) {
            // 菜单输入
            Scanner scanner = new Scanner(System.in);
            clientCommandMenu.exec(scanner);
            String key = clientCommandMenu.getCommandInput();

            // 根据菜单输入，选择正确的命令收集器
            BaseCommand command = commandMap.get(key);
            if (null == command) {
                System.err.println("无法识别[" + key + "]指令，请重新输入!");
                continue;
            }

            // 执行命令收集器
            switch (key) {
                case LoginConsoleCommand.KEY:
                    command.exec(scanner);
                    LoginConsoleCommand logCmd = (LoginConsoleCommand) command;
                    log.info("本次输入的 username 为：" + logCmd.getUserName());
                    log.info("本次输入的 password 为：" + logCmd.getPassword());
                    break;
                case LogoutConsoleCommand.KEY:
                    command.exec(scanner);
                    break;
            }
        }
    }

    static void initCommandMap() {
        commandMap = new HashMap<>();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        LogoutConsoleCommand logoutConsoleCommand = new LogoutConsoleCommand();
        commandMap.put(clientCommandMenu.getKey(), clientCommandMenu);
        commandMap.put(loginConsoleCommand.getKey(), loginConsoleCommand);
        commandMap.put(logoutConsoleCommand.getKey(), logoutConsoleCommand);
        clientCommandMenu.setAllCommand(commandMap);
    }

}
