package im.util;


import im.protocol.attr.Attributes;
import io.netty.channel.Channel;

/**
 * @ClassName: LoginUtil
 * @Author: lxt
 * @Description: 登录工具类
 * @Version: 1.0
 */
public class LoginUtil {
    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel){
        return channel.attr(Attributes.LOGIN).get() != null;
    }
}
