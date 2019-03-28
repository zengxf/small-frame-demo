import cn.zxf.server_impl.UserServerImpl;
import cn.zxf.server.UserServer;

// Ctrl+Alt+U 可视化查看
module cn.zxf.server_impl {
    requires cn.zxf.server;
    provides UserServer with UserServerImpl;
}