package test.simple;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 版本兼容性测试
 * <p/>
 * Created by ZXFeng on 2025/12/31
 */
@Slf4j
public class VersionCompMsgTest {

    String filePath = "out/msg.dat";

    /*** 序列化并保存到文件 */
    @Test
    @Ignore
    public void serToFile() {
        VersionCompMsg.CompMsg message = VersionCompMsg.CompMsg.newBuilder()
                .setId(8899)
                .setName("Fa")
                .setStr03("str03")
                .setStr04("str04")
                .setStr05("str05")
                // .setStr06("str06")   // 删字段前设置
                .setStr07("str07")
                // .setStr08("str08")   // def: ""
                // .setStr08(null)      // NPE err
                .setStr09("str09")
                .setStr99("str99")
                .build();
        log.info("Build message: \n{\n{}}", message);
        log.info("Build msg str08: [{}]", message.getStr08());

        // 将 Protobuf 对象，序列化成二进制字节数组
        byte[] data = message.toByteArray();

        // 可以用于网络传输, 保存到内存或外存
        try {
            Files.write(Paths.get(filePath), data);
            log.info("文件保存成功！");
        } catch (IOException e) {
            log.error("保存出错", e);
        }
    }

    /**
     * 从文件中反序列化出来。
     * <br/>
     * proto 加了字段、删除字段，反序列化时不会出错，兼容性可以。
     * <br/><br/>
     * str06 删除后，输出会变成
     * <pre>
     * id: 8899
     * name: "Fa"
     * ...
     * str99: "str99"
     * 6: "str06"
     * </pre>
     */
    @Test
    public void desFromFile() throws IOException {
        byte[] data = Files.readAllBytes(Paths.get(filePath));
        log.info("读取文件成功！");

        VersionCompMsg.CompMsg message = VersionCompMsg.CompMsg.parseFrom(data);
        log.info("Read parse message: \n{\n{}}", message);
        log.info("Read parse msg str08: [{}]", message.getStr08());
        log.info("Read parse msg str10: [{}]", message.getStr10()); // def: ""
    }

}
