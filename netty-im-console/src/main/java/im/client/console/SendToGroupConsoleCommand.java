package im.client.console;

import im.protocol.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @ClassName: SendToGroupConsoleCommand
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        GroupMessageRequestPacket groupMessageRequestPacket = new GroupMessageRequestPacket();
        System.out.println("请输入groupId message：");
        String groupId = scanner.next();
        String message = scanner.next();
        groupMessageRequestPacket.setToGroupId(groupId);
        groupMessageRequestPacket.setMessage(message);
        channel.writeAndFlush(groupMessageRequestPacket);
    }
}
