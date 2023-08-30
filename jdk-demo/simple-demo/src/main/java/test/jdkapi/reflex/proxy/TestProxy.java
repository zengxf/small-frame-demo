package test.jdkapi.reflex.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

/**
 * <pre>
 * Proxy.newProxyInstance 调用路径
 *   #getProxyConstructor()
 *   ProxyBuilder #build()
 *     #defineProxyClass()
 *     ProxyGenerator #generateProxyClass() // 生成字节码；并 dump 到文件
 * </pre>
 * Created by ZXFeng on 2023/7/24
 */
@Slf4j
public class TestProxy {

    public static void main(String[] args) {
        // 参考：ProxyGenerator #generateProxyClass() 和 #saveGeneratedFiles
        System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");

        IService service = new UserService();
        MyHandler handler = new MyHandler(service);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        log.info("Thread-CL: [{}]", classLoader);
        log.info("Class--CL: [{}]", TestProxy.class.getClassLoader());

        Class<?>[] interfaces = {IService.class};
        IService proxy = (IService) Proxy.newProxyInstance(classLoader, interfaces, handler);

        // service.say();
        proxy.say(); // 可反编译 dump 出的 class，了解其原理

        log.info("proxy-toString(): [{}]", proxy);
    }

}
