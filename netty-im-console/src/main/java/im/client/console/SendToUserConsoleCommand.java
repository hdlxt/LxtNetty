package im.client.console;

import im.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @ClassName: SendToUserConsoleCommand
 * @Author: lxt
 * @Description: 发消息指令
 * @Version: 1.0
 */
public class SendToUserConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        String toUserId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId,message));
    }
}
