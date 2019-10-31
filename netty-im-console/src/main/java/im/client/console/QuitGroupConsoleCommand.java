package im.client.console;

import im.protocol.request.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @ClassName: QuitGroupConsoleCommand
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        QuitGroupRequestPacket quitGroupRequestPacket = new QuitGroupRequestPacket();

        System.out.println("输入groupId,退出群聊：");
        String groupId = scanner.next();

        quitGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
