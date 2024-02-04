package cn.zxf.reactor_demo.flux;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * 复合测试
 * <p/>
 * Created by ZXFeng on 2024/2/4
 */
@Slf4j(topic = "out")
public class ComplexTest {

    public static void main(String[] args) {
        Flux.range(1, 10)       // sign_demo_001 初始化流
                // .log("init")             // sign_demo_101 打印日志

                .filter(i -> i % 2 == 0)   // sign_demo_201 过滤
                // .log("filter")

                .skip(1)            // sign_demo_301 跳过
                // .log("skip")

                // .limitRate(50)

                .sort()                     // sign_demo_401 排序
                // .log("sort")

                .map(i -> "sign@" + i)      // sign_demo_501 转换
                // .log("map")

                .subscribe(log::info)       // sign_demo_601 订阅并消费
        ;
    }

}
