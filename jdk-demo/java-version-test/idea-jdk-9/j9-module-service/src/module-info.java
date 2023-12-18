import cn.zxf.j9.service.impl.*;

module cn.zxf.j9.service.s1 {
    exports cn.zxf.j9.service;

    provides cn.zxf.j9.service.PrintService with HelloPrintService,SayPrintService;
}