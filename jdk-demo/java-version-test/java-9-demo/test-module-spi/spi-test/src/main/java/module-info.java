import cn.zxf.server.UserServer;

// Ctrl+Alt+U 可视化查看
module cn.zxf.test {
    requires cn.zxf.server;
    uses UserServer; // 需要声明下
}