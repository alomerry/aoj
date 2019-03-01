package mo.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class DIYMessage {
    //消息类型
    int MessageType;

    //消息实体
    Object object;

    /**
     * 通过参数构造自定义消息
     *
     * @param messageType 消息类型
     * @param object      消息实体
     */
    public DIYMessage(int messageType, Object object) {
        MessageType = messageType;
        this.object = object;
    }
}