package test.base.decoder.byte2int;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/19.
 */
@Slf4j
public class IntegerAddDecoder extends ReplayingDecoder<IntegerAddDecoder.Status> {

    enum Status {
        PARSE_1, PARSE_2
    }

    private int first;
    private int second;

    public IntegerAddDecoder() {
        // 构造函数中，需要初始化父类的 state 属性，表示当前阶段
        super(Status.PARSE_1);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        switch (state()) {
            case PARSE_1:
                // 从装饰器 ByteBuf 中读取数据
                first = in.readInt();
                // 第一步解析成功，
                // 进入第二步，并且设置“读指针断点”为当前的读取位置
                log.info("p1 - first: [{}]", first);
                checkpoint(Status.PARSE_2);
                break;
            case PARSE_2:
                second = in.readInt();
                Integer sum = first + second;
                out.add(sum);
                log.info("p2 - first [{}] + second [{}] = sum [{}]", first, second, sum);
                checkpoint(Status.PARSE_1);
                break;
            default:
                break;
        }
    }

}