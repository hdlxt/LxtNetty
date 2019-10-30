package im.codec;

import im.protocol.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ClassName: PacketDecoder
 * @Author: lxt
 * @Description: 解码处理器
 * @Version: 1.0
 */
public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List list) throws Exception {
        list.add(PacketCodeC.INSTANCE.decode(byteBuf));
    }
}
