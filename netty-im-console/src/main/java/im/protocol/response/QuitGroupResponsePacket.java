package im.protocol.response;

import im.protocol.packet.Packet;
import lombok.Data;

import static im.protocol.command.Command.QUIT_GROUP_RESPONSE;
/**
 * @ClassName: QuitGroupResponsePacket
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
