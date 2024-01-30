package cn.zxf.reactor_demo;

import reactor.core.publisher.Flux;

/**
 * <p/>
 * Created by ZXFeng on 2024/1/30
 */
public class TestCall {

    public static void main(String[] args) {
        // ReactorDebugAgent.init(); // 没用

        Flux<Integer> flux = Flux.range(0, 5);
        // Hooks.addCallSiteInfo(flux, "Flux.range\n foo.Bar.baz(Bar.java:21)"); // 没用

        flux
                .log()
                .subscribe();
    }

}
