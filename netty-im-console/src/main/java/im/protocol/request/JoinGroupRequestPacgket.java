package im.protocol.request;

import im.protocol.packet.Packet;
import lombok.Data;

import static im.protocol.command.Command.JOIN_GROUP_REQUEST;
/**
 * @ClassName: JoinGroupRequestPacgket
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
@Data
public class JoinGroupRequestPacgket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
