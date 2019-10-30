package im.client.console;

import im.protocol.request.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @ClassName: LogoutConsoleCommand
 * @Author: lxt
 * @Description: 登出指令
 * @Version: 1.0
 */
public class LogoutConsoleCommand implements ConsoleCommand{

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(logoutRequestPacket);
    }
}
