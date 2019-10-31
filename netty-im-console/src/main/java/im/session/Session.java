package im.session;

import lombok.Data;

/**
 * @ClassName: Session
 * @Author: lxt
 * @Description: 存储用户信息
 * @Version: 1.0
 */
@Data
public class Session {
    private String userId;
    private  String userName;
    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Session() {
    }
}
