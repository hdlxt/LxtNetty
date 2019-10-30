package im.protocol.response;

import im.protocol.packet.Packet;
import lombok.Data;
import static im.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * @ClassName: MessageResponsePacket
 * @Author: lxt
 * @Description: 消息响应
 * @Version: 1.0
 */
@Data
public class MessageResponsePacket extends Packet {
    private String message;
    private String fromUserId;
    private String fromUserName;

    public MessageResponsePacket() {
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
