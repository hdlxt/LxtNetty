package im.client.console;

import im.protocol.request.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @ClassName: ListGroupMembersConsoleCommand
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
public class ListGroupMembersConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        ListGroupMembersRequestPacket listGroupMembersRequestPacket = new ListGroupMembersRequestPacket();

        System.out.println("请输入groupId，获取成员列表：");
        String groupId = scanner.next();

        listGroupMembersRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
