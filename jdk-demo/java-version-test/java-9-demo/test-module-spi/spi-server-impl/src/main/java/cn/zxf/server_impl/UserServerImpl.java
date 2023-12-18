package cn.zxf.server_impl;

import cn.zxf.server.UserServer;

public class UserServerImpl implements UserServer {

    @Override
    public void test() {
        System.out.println("test spi");
    }

}
