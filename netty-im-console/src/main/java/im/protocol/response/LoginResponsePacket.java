package im.protocol.response;

import im.protocol.packet.Packet;
import lombok.Data;

import static im.protocol.command.Command.LOGIN_RESPONSE;

/**
 * @ClassName: LoginResponsePacket
 * @Author: lxt
 * @Description: 登录响应
 * @Version: 1.0
 */
@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String userName;

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {

        return LOGIN_RESPONSE;
    }

}
