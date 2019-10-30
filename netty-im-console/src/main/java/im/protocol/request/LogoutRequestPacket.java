package im.protocol.request;

import im.protocol.packet.Packet;
import lombok.Data;
import static im.protocol.command.Command.LOGOUT_REQUEST;


@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {

        return LOGOUT_REQUEST;
    }
}
