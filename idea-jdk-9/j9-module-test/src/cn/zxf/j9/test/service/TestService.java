package cn.zxf.j9.test.service;


import cn.zxf.j9.service.PrintService;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by zengxf on 2017/10/11.
 */
public class TestService {

    public static void main(String[] arr) {
        ServiceLoader<PrintService> loader = ServiceLoader.load(PrintService.class);
        Iterator<PrintService> iterator = loader.iterator();
        while (iterator.hasNext()) {
            PrintService service = iterator.next();
            service.print();
        }

        System.out.println("------------");

        loader.stream().forEach(provider -> provider.get().print());
    }

}
