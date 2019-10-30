package im.protocol.request;

import im.protocol.packet.Packet;
import lombok.Data;

import static im.protocol.command.Command.LOGIN_REQUEST;
/**
 * @ClassName: LoginRequestPacket
 * @Author: lxt
 * @Description: 登录请求包
 * @Version: 1.0
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String userName;

    private String password;


    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
