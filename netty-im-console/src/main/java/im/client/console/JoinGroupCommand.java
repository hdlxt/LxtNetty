package im.client.console;

import im.protocol.request.JoinGroupRequestPacgket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @ClassName: JoinGroupCommand
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
public class JoinGroupCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacgket joinGroupRequestPacgket = new JoinGroupRequestPacgket();

        System.out.println("输入groupId 加入群聊：");
        String groupId = scanner.next();

        joinGroupRequestPacgket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequestPacgket);

    }
}
