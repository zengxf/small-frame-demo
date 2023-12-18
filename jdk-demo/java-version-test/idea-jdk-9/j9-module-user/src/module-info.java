module cn.zxf.j9.user {
    exports cn.zxf.j9.user;
    exports cn.zxf.j9.user.open;
    exports cn.zxf.j9.user.open2;

    opens cn.zxf.j9.user.open;
    opens cn.zxf.j9.user.open2 to cn.zxf.j9.test;
}