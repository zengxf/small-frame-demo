// Ctrl+Alt+U 可视化查看
module cn.zxf.m1 {
    exports cn.zxf.test_user;
    opens cn.zxf.test_user;
    requires java.base;
}