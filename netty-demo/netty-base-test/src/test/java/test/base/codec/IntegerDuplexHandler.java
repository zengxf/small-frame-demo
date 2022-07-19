package test.base.codec;

import io.netty.channel.CombinedChannelDuplexHandler;
import test.base.decoder.byte2int.Byte2IntegerDecoder;
import test.base.encoder.Integer2ByteEncoder;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/19.
 */
public class IntegerDuplexHandler extends CombinedChannelDuplexHandler<Byte2IntegerDecoder, Integer2ByteEncoder> {

    public IntegerDuplexHandler() {
        super(new Byte2IntegerDecoder(), new Integer2ByteEncoder());
    }

}
