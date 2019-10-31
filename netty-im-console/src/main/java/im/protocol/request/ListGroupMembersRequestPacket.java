package im.protocol.request;

import im.protocol.packet.Packet;
import lombok.Data;

import static im.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;
/**
 * @ClassName: ListGroupMembersRequestPacket
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
@Data
public class ListGroupMembersRequestPacket extends Packet {
    private String groupId;
    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
