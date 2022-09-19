package im.client.client;

import im.client.command.*;
import im.client.sender.ChatSender;
import im.client.sender.LoginSender;
import im.client.session.ClientSession;
import im.common.cocurrent.FutureTaskScheduler;
import im.common.dto.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
@Service
@Data
@Slf4j
public class CommandController {

    @Autowired
    private List<BaseCommand> commands;
    @Autowired
    private ClientCommandMenu clientCommandMenu;
    private Map<String, BaseCommand> commandMap;
    private ClientSession session;
    @Autowired
    @Lazy
    private ChatNettyClient chatNettyClient;
    private Channel channel;
    @Autowired
    private ChatSender chatSender;
    @Autowired
    private LoginSender loginSender;
    private boolean connectFlag = false;
    private User user;

    GenericFutureListener<ChannelFuture> closeListener = (ChannelFuture f) -> {
        log.info(new Date() + ": 连接已经断开……");
        channel = f.channel();
        // 创建会话
        ClientSession session = channel.attr(ClientSession.SESSION_KEY).get();
        session.close();
        // 唤醒用户线程
        notifyCommandThread();
    };

    GenericFutureListener<ChannelFuture> connectedListener = (ChannelFuture f) -> {
        final EventLoop eventLoop = f.channel().eventLoop();
        if (!f.isSuccess()) {
            log.info("连接失败!在10s之后准备尝试重连!");
            eventLoop.schedule(() -> chatNettyClient.doConnect(), 10, TimeUnit.SECONDS);
            connectFlag = false;
        } else {
            connectFlag = true;
            log.info("疯狂创客圈 IM 服务器 连接成功!");
            channel = f.channel();
            // 创建会话
            session = new ClientSession(channel);
            session.setConnected(true);
            channel.closeFuture().addListener(closeListener);
            // 唤醒用户线程
            notifyCommandThread();
        }
    };

    public void initCommandMap() {
        commandMap = commands.stream()
                .collect(Collectors.toMap(BaseCommand::getKey, Function.identity()));
        clientCommandMenu.setAllCommand(commandMap);
    }

    public void startConnectServer() {
        FutureTaskScheduler.add(() -> {
            chatNettyClient.setConnectedListener(connectedListener);
            chatNettyClient.close();
            chatNettyClient.doConnect();
        });
    }

    public synchronized void notifyCommandThread() {
        // 唤醒，命令收集程
        this.notify();
    }

    public synchronized void waitCommandThread() {
        // 休眠，命令收集线程
        try {
            this.wait();
        } catch (InterruptedException e) {
            log.error("error", e);
        }
    }

    public void commandThreadRunning() {
        Thread.currentThread().setName("命令线程");
        while (true) {
            // 建立连接
            while (!connectFlag) {
                // 开始连接
                startConnectServer();
                waitCommandThread();
            }
            // 处理命令
            while (null != session) {
                Scanner scanner = new Scanner(System.in);
                clientCommandMenu.exec(scanner);
                String key = clientCommandMenu.getCommandInput();
                BaseCommand command = commandMap.get(key);
                if (command == null) {
                    System.err.println("无法识别[" + key + "]指令，请重新输入!");
                    continue;
                }
                switch (key) {
                    case ChatConsoleCommand.KEY:
                        command.exec(scanner);
                        startOneChat((ChatConsoleCommand) command);
                        break;
                    case LoginConsoleCommand.KEY:
                        command.exec(scanner);
                        startLogin((LoginConsoleCommand) command);
                        break;
                    case LogoutConsoleCommand.KEY:
                        command.exec(scanner);
                        startLogout(command);
                        break;
                }
            }
        }
    }

    // 发送单聊消息
    private void startOneChat(ChatConsoleCommand c) {
        if (!isLogin()) {
            log.info("还没有登录，请先登录");
            return;
        }
        chatSender.setSession(session);
        chatSender.setUser(user);
        chatSender.sendChatMsg(c.getToUserId(), c.getMessage());
    }

    private void startLogin(LoginConsoleCommand command) {
        if (!isConnectFlag()) {
            log.info("连接异常，请重新建立连接");
            return;
        }
        User user = new User();
        user.setUid(command.getUserName());
        user.setToken(command.getPassword());
        user.setDevId("1111");
        this.user = user;
        session.setUser(user);
        loginSender.setUser(user);
        loginSender.setSession(session);
        loginSender.sendLoginMsg();
    }

    private void startLogout(BaseCommand command) {
        if (!isLogin()) {
            log.info("还没有登录，请先登录");
            return;
        }
        channel.close();
    }

    public boolean isLogin() {
        if (session == null) {
            log.info("session is null");
            return false;
        }
        return session.isLogin();
    }

}
