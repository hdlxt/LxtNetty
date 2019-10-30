package im.protocol.request;

import im.protocol.packet.Packet;
import lombok.Data;

import static im.protocol.command.Command.MESSAGE_REQUEST;

/**
 * @ClassName: MessageRequestPacket
 * @Author: lxt
 * @Description: 消息包
 * @Version: 1.0
 */
@Data
public class MessageRequestPacket extends Packet {

    private  String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    public MessageRequestPacket() {
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
