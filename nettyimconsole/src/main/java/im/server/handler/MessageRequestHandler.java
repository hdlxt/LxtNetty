package im.server.handler;

import im.protocol.request.MessageRequestPacket;
import im.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @ClassName: MessageRequestHandler
 * @Author: lxt
 * @Description: 消息请求处理器
 * @Version: 1.0
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        System.out.println(LocalDateTime.now() +":收到客户端消息："+messageRequestPacket.getMessage());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("服务端回复："+messageRequestPacket.getMessage());
        channelHandlerContext.channel().writeAndFlush(messageResponsePacket);
    }

}
