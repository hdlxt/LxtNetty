package im.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @ClassName: ConsoleCommand
 * @Author: lxt
 * @Description: 指令接口
 * @Version: 1.0
 */
public interface ConsoleCommand {
     void exec(Scanner scanner, Channel channel);
}
