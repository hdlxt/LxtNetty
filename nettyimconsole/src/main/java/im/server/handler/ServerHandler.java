package im.server.handler;

import im.protocol.packet.Packet;
import im.protocol.packet.PacketCodeC;
import im.protocol.request.LoginRequestPacket;
import im.protocol.request.MessageRequestPacket;
import im.protocol.response.LoginResponsePacket;
import im.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;

/**
 * @ClassName: ServerHandler
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf byteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        // 判断是否是登录请求数据包
        if(packet instanceof LoginRequestPacket){
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            // 登录校验
            if(vaild(loginRequestPacket)){
                loginResponsePacket.setSuccess(true);
            }else{
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("帐号密码校验失败");
            }

            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }else if(packet instanceof MessageRequestPacket){
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(LocalDateTime.now() +":收到客户端消息："+messageRequestPacket.getMessage());
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复："+messageRequestPacket.getMessage());
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean vaild(LoginRequestPacket loginRequestPacket){
        return true;
    }

}
