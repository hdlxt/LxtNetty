package im.protocol.attr;

import im.session.Session;
import io.netty.util.AttributeKey;

/**
 * @ClassName: Attributes
 * @Author: lxt
 * @Description: 属性标识
 * @Version: 1.0
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
