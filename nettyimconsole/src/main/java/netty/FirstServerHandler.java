package netty;

import common.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * @ClassName: FirstServerHandler
 * @Author: Administrator
 * @Description: 服务端逻辑处理器
 * @Version: 1.0
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(LocalDateTime.now() +"：服务端读到的数据->"+byteBuf.toString(Charset.forName(Constants.DEFAULT_CHARSET)));
        System.out.println(LocalDateTime.now() +":服务端写出数据");
        ByteBuf outByteBuf = getByteBuf(ctx);
        ctx.channel().writeAndFlush(outByteBuf);
    }
    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        byte[] bytes = "你好，笨鸟先飞!".getBytes(Charset.forName(Constants.DEFAULT_CHARSET));
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
