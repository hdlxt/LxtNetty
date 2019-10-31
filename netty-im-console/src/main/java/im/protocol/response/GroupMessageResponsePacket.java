package im.protocol.response;

import im.protocol.packet.Packet;
import im.session.Session;
import lombok.Data;

import static im.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {

        return GROUP_MESSAGE_RESPONSE;
    }
}
