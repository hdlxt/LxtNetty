package im.protocol.packet;

import im.protocol.command.Command;
import im.protocol.request.LoginRequestPacket;
import im.protocol.response.LoginResponsePacket;
import im.serialize.Serializer;
import im.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: PacketCodeC
 * @Author: lxt
 * @Description: 编码和解码
 * @Version: 1.0
 */
public class PacketCodeC {
    /**
     * 魔法值，协议标识
     */
    public  static final int MAGIC_NUMBER = 0x12345678;

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte,Class<? extends  Packet>> packetTypeMap;

    private final Map<Byte,Serializer> serializerMap;

    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerielizerAlgorithm(),serializer);

    }



    /**
     * 编码
     * @param packet
     * @return
     */
    public ByteBuf encode(Packet packet){
        // 创建ByteBuf对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // 序列化 Java对象
        byte[] bytes = Serializer.DEFAULT.seriallize(packet);

        // 实际编码过程
        // 魔法值
        byteBuf.writeInt(MAGIC_NUMBER);
        // 版本号
        byteBuf.writeByte(packet.getVersion());
        // 序列化算法
        byteBuf.writeByte(Serializer.DEFAULT.getSerielizerAlgorithm());
        // 指令
        byteBuf.writeByte(packet.getCommand());
        // 字节长度
        byteBuf.writeInt(bytes.length);
        // 数据
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    /**
     * 解码
     * @param byteBuf
     * @return
     */
    public Packet decode(ByteBuf byteBuf){
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);
        byte serlizerAlgorithm = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serlizerAlgorithm);
        if(requestType != null && serializer != null){
            return serializer.deserialize(requestType,bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serialzeAlgorthm){
        return serializerMap.get(serialzeAlgorthm);
    }

    private Class<? extends Packet> getRequestType(byte command){
        return packetTypeMap.get(command);
    }
}
