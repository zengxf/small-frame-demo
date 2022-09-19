package im.common.dto;

import cn.hutool.core.util.StrUtil;
import im.common.dto.msg.ProtoMsg;
import lombok.Data;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
@Data
public class ChatMsg {

    // 消息类型 => 1：纯文本; 2：音频; 3：视频; 4：地理位置; 5：其他
    public enum MSGTYPE {
        TEXT,
        AUDIO,
        VIDEO,
        POS,
        OTHER;
    }

    public ChatMsg(User user) {
        if (user == null) {
            return;
        }
        this.user = user;
        this.setTime(System.currentTimeMillis());
        this.setFrom(user.getUid());
        this.setFromNick(user.getNickName());
    }

    private User user;
    private long msgId;
    private String from;
    private String to;
    private long time;
    private MSGTYPE msgType;
    private String content;
    private String url;          // 多媒体地址
    private String property;     // 附加属性
    private String fromNick;     // 发送者昵称
    private String json;         // 附加


    public void fillMsg(ProtoMsg.MessageRequest.Builder msg) {
        if (msgId > 0) {
            msg.setMsgId(msgId);
        }
        if (StrUtil.isNotEmpty(from)) {
            msg.setFrom(from);
        }
        if (StrUtil.isNotEmpty(to)) {
            msg.setTo(to);
        }
        if (time > 0) {
            msg.setTime(time);
        }
        if (msgType != null) {
            msg.setMsgType(msgType.ordinal());
        }
        if (StrUtil.isNotEmpty(content)) {
            msg.setContent(content);
        }
        if (StrUtil.isNotEmpty(url)) {
            msg.setUrl(url);
        }
        if (StrUtil.isNotEmpty(property)) {
            msg.setProperty(property);
        }
        if (StrUtil.isNotEmpty(fromNick)) {
            msg.setFromNick(fromNick);
        }
        if (StrUtil.isNotEmpty(json)) {
            msg.setJson(json);
        }
    }

}
