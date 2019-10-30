package im.spliter;

import im.protocol.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @ClassName: Spliter
 * @Description: 拒绝非本协议连接 & 基于长度域拆包器
 * @Author: lxt
 * @Date: 2019-10-30 12:53
 * @Version 1.0
 **/
public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;
//
    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

//    @Override
//    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
//        // 屏蔽非本协议的客户端
//        if (in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
//            ctx.channel().close();
//            return null;
//        }
//
//        return super.decode(ctx, in);
//    }

    @Override
    protected Object decode(ChannelHandlerContext ctx,ByteBuf in) throws Exception {
        if(in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER){
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx,in);
    }
}
