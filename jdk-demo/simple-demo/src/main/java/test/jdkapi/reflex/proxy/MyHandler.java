package test.jdkapi.reflex.proxy;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <br>
 * Created by ZXFeng on 2023/7/24
 */
@Slf4j
@AllArgsConstructor
public class MyHandler implements InvocationHandler {

    private Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("--------------------------");

        // log.info("proxy: [{}]", proxy); // 调用 proxy 方法就会调用进来，所以 toString() 也会进来
        log.info("proxy: [{}]", proxy.getClass());
        log.info("method: [{}]", method);
        log.info("args: [{}]", args);

        log.info("--------------------------");

        return method.invoke(target, args); // 调用目标对象
    }

}
