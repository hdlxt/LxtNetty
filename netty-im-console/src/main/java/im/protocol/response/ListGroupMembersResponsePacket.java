package im.protocol.response;

import im.protocol.packet.Packet;
import im.session.Session;
import lombok.Data;

import java.util.List;

import static im.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;
/**
 * @ClassName: ListGroupResponsePacket
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {
    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
