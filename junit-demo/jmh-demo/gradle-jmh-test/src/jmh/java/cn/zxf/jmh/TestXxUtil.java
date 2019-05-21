package cn.zxf.jmh;

import org.openjdk.jmh.annotations.Benchmark;

import cn.zxf.utils.XxUtil;

public class TestXxUtil {

    @Benchmark
    public void testUuid() {
        XxUtil.uuid();
    }

}
