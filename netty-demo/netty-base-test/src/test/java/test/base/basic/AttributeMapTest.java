package test.base.basic;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/23.
 */
@Slf4j
public class AttributeMapTest {

    @Test
    public void test() {
        EmbeddedChannel channel = new EmbeddedChannel(new LoggingHandler(LogLevel.DEBUG));
        AttributeKey<Integer> key = AttributeKey.valueOf("test-k");
        key = AttributeKey.valueOf("test-k"); // 已有直接返回

        Attribute<Integer> attr = channel.attr(key);
        attr.set(10);

        Attribute<Integer> getAttr = channel.attr(key);
        log.info("get: [{}]", getAttr.get());
    }

    @Test // 简单写法
    public void testSimple() {
        EmbeddedChannel channel = new EmbeddedChannel(); // 实际是 DefaultAttributeMap
        AttributeKey<Integer> key = AttributeKey.newInstance("test-k0");
        // key = AttributeKey.newInstance("test-k0"); // 已有会抛异常
        channel.attr(key).set(100);
        log.info("get: [{}]", channel.attr(key).get());
    }

    @Test
    public void testHas() {
        EmbeddedChannel channel = new EmbeddedChannel(new LoggingHandler(LogLevel.DEBUG));
        AttributeKey<Integer> keyNo = AttributeKey.newInstance("test-k0");
        AttributeKey<Integer> keyNo1 = AttributeKey.newInstance("test-k1");

        channel.attr(keyNo).set(100);
        log.info("has: [{}]", channel.hasAttr(keyNo));
        log.info("get: [{}]", channel.attr(keyNo).get());
        log.info("-------------");

        channel.attr(keyNo).set(null);
        log.info("has: [{}]", channel.hasAttr(keyNo));
        log.info("get: [{}]", channel.attr(keyNo).get());
        log.info("-------------");

        log.info("has: [{}]", channel.hasAttr(keyNo1));
        log.info("get: [{}]", channel.attr(keyNo1).get());
    }

}
