package im.client.console;

import im.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @ClassName: LoginConsoleCommand
 * @Author: lxt
 * @Description: 登录指令
 * @Version: 1.0
 */
public class LoginConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        System.out.println("请输入用户名登录：");
        String line = scanner.nextLine();
        loginRequestPacket.setUserName(line);
        loginRequestPacket.setPassword("pwd");
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }
    private static void waitForLoginResponse(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
