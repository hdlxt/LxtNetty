package im.client.handler;

import im.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @ClassName: LoginResponseHandler
 * @Author: lxt
 * @Description: 登录响应处理器
 * @Version: 1.0
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        System.out.println(LocalDateTime.now()+":收到服务端消息："+messageResponsePacket.getMessage());
    }
}
