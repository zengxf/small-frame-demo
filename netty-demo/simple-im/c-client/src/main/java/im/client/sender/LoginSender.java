package im.client.sender;

import im.client.converter.LoginMsgConverter;
import im.common.dto.msg.ProtoMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
@Service
@Slf4j
public class LoginSender extends BaseSender {

    public void sendLoginMsg() {
        if (!super.isConnected()) {
            log.info("还没有建立连接!");
            return;
        }
        log.info("构造登录消息");
        ProtoMsg.Message message = LoginMsgConverter.build(super.getUser(), super.getSession());
        log.info("发送登录消息");
        super.sendMsg(message);
    }

}