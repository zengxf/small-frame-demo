package im.client.sender;

import im.client.converter.ChatMsgConverter;
import im.common.dto.ChatMsg;
import im.common.dto.msg.ProtoMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
@Service
@Slf4j
public class ChatSender extends BaseSender {

    public void sendChatMsg(String toUid, String content) {
        log.info("发送消息 startConnectServer");
        ChatMsg chatMsg = new ChatMsg(getUser());
        chatMsg.setContent(content);
        chatMsg.setMsgType(ChatMsg.MSGTYPE.TEXT);
        chatMsg.setTo(toUid);
        chatMsg.setMsgId(System.currentTimeMillis());
        ProtoMsg.Message message = ChatMsgConverter.build(chatMsg, getUser(), getSession());
        super.sendMsg(message);
    }

    @Override
    protected void sendSuccess(ProtoMsg.Message message) {
        log.info("发送成功: " + message.getMessageRequest().getContent());
    }

    @Override
    protected void sendFailed(ProtoMsg.Message message) {
        log.info("发送失败: " + message.getMessageRequest().getContent());
    }

}
