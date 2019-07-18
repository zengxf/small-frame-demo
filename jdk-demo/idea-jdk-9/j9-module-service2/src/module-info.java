import cn.zxf.j9.service.impl2.HelloPrintService;

module cn.zxf.j9.service.s2 {
    requires cn.zxf.j9.service.s1;

    provides cn.zxf.j9.service.PrintService with HelloPrintService;
}