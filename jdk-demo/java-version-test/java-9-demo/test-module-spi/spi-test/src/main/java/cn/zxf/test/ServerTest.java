package cn.zxf.test;


import cn.zxf.server.UserServer;

import java.util.ServiceLoader;

/**
 * Created by zengxf on 2018/9/10.
 */
public class ServerTest {

    public static void main(String[] arr) {
        System.out.println(ServerTest.class.getModule().canUse(UserServer.class));
//        ServerTest.class.getModule().addUses(UserServer.class);

        ServiceLoader.load(UserServer.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .forEach(userServer -> {
                    System.out.println(userServer);
                    userServer.test();
                });
    }

}
