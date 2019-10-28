package im.protocol.packet;

import lombok.Data;

/**
 * @ClassName: Packet
 * @Author: lxt
 * @Description: 通信协议-包对象
 * @Version: 1.0
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 抽象的指令方法，子类必须实现
     * @return
     */
    public abstract Byte getCommand();
}
