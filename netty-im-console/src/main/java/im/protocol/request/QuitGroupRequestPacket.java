package im.protocol.request;

import im.protocol.packet.Packet;
import lombok.Data;

import static im.protocol.command.Command.QUIT_GROUP_REQUEST;
/**
 * @ClassName: QuitGroupRequestPacket
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
@Data
public class QuitGroupRequestPacket extends Packet {
    private String groupId;
    @Override
    public Byte getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}
