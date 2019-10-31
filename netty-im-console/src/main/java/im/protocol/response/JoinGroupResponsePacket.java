package im.protocol.response;

import im.protocol.packet.Packet;
import lombok.Data;

import static im.protocol.command.Command.JOIN_GROUP_RESPONSE;
/**
 * @ClassName: JoinGroupResponsePacket
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;

    private  boolean success;

    private  String reason;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}
