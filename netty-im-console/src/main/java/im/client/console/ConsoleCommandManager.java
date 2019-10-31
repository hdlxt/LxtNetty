package im.client.console;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @ClassName: ConsoleCommandManager
 * @Author: lxt
 * @Description: 指令管理
 * @Version: 1.0
 */
public class ConsoleCommandManager implements ConsoleCommand{

    private Map<String,ConsoleCommand> consoleCommandMaP;

    public ConsoleCommandManager() {
        this.consoleCommandMaP = new HashMap<>();
        consoleCommandMaP.put("sendToUser",new SendToUserConsoleCommand());
        consoleCommandMaP.put("logout",new LogoutConsoleCommand());
        consoleCommandMaP.put("login",new LoginConsoleCommand());
        consoleCommandMaP.put("createGroup",new CreateGroupConsoleCommand());
        consoleCommandMaP.put("joinGroup",new JoinGroupCommand());
        consoleCommandMaP.put("listMembersGroup",new ListGroupMembersConsoleCommand());
        consoleCommandMaP.put("quitGroup",new QuitGroupConsoleCommand());
        consoleCommandMaP.put("sendToGroup",new SendToGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        // 获取第一个指令
        String command = scanner.next();

        ConsoleCommand consoleCommand = consoleCommandMaP.get(command);
        if(consoleCommand != null){
            consoleCommand.exec(scanner,channel);
        }else{
            System.out.println("无法识别【"+command+"】指令，请重新输入");
        }
    }
}
