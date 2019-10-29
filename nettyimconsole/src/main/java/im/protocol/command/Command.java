package im.protocol.command;

/**
 * @ClassName: Command
 * @Author: lxt
 * @Description: 指令集接口
 * @Version: 1.0
 */
public interface Command {
    /**
     * 登录请求指令
     */
    Byte LOGIN_REQUEST = 1;
    /**
     * 登录响应
     */
    Byte LOGIN_RESPONSE = 2;
    /**
     * 消息请求
     */
    Byte MESSAGE_REQUEST = 3;
    /**
     * 消息响应
     */
    Byte MESSAGE_RESPONSE = 4;
}
