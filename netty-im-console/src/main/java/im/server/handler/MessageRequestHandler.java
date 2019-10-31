package im.server.handler;

import im.protocol.request.MessageRequestPacket;
import im.protocol.response.MessageResponsePacket;
import im.session.Session;
import im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @ClassName: MessageRequestHandler
 * @Author: lxt
 * @Description: 消息请求处理器
 * @Version: 1.0
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();


    private MessageRequestHandler() {

    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        System.out.println(LocalDateTime.now() +":收到客户端消息："+messageRequestPacket.getMessage());
        // 拿到对方会话信息
        Session session = SessionUtil.getSession(channelHandlerContext.channel());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());

        // 拿到消息接收方的channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        // 将消息发送给对方
        if(toUserChannel != null && SessionUtil.hasLogin(toUserChannel)){
            toUserChannel.writeAndFlush(messageResponsePacket);
        }else{
            System.out.println("【"+messageRequestPacket.getToUserId()+"】 不在线 发送失败");
        }
        channelHandlerContext.channel().writeAndFlush(messageResponsePacket);
    }

}
