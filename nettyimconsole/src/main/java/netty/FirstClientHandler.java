package netty;

import common.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * @ClassName: FirstClientHandler
 * @Author: lxt
 * @Description: 客户端处理逻辑
 * @Version: 1.0
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println(LocalDateTime.now()+":客户端写出数据");
        // 获取数据
        ByteBuf byteBuf = getByteBuf(ctx);
        // 写数据
        ctx.channel().writeAndFlush(byteBuf);


    }
    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        // 获取二进制抽象ByteBuf
        ByteBuf byteBuf = ctx.alloc().buffer();
        // 准备数据，指定字符串的字符集为utf-8
        byte[] bytes = "你好，笨鸟！".getBytes(Charset.forName(Constants.DEFAULT_CHARSET));
        // 填充数据到ButeBuf
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(LocalDateTime.now()+":客户端读到数据->"+byteBuf.toString(Charset.forName(Constants.DEFAULT_CHARSET)));
    }
}
