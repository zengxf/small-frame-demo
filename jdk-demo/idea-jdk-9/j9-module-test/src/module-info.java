module cn.zxf.j9.test {
    requires cn.zxf.j9.user;
    requires cn.zxf.j9.open;
    requires cn.zxf.j9.optional.o1;
//    requires static cn.zxf.j9.optional.o2;

    requires cn.zxf.j9.service.s1;
    requires cn.zxf.j9.service.s2;


    uses cn.zxf.j9.service.PrintService;
}