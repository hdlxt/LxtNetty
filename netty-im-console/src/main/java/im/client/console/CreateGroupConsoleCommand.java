package im.client.console;

import im.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @ClassName: CreateGroupConsoleCommand
 * @Author: lxt
 * @Description: 创建群聊指令
 * @Version: 1.0
 */
public class CreateGroupConsoleCommand implements ConsoleCommand{
    private static final String USER_ID_SPLITER = ",";
    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();

        System.out.println("【拉人群聊】输入 userId 列表，userId 之间英文逗号：");
        String userId = scanner.next();
        createGroupRequestPacket.setUserIdList(Arrays.asList(userId.split(USER_ID_SPLITER)));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
