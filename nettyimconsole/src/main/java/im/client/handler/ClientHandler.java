package im.client.handler;

import im.protocol.packet.Packet;
import im.protocol.packet.PacketCodeC;
import im.protocol.request.LoginRequestPacket;
import im.protocol.response.LoginResponsePacket;
import im.protocol.response.MessageResponsePacket;
import im.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @ClassName: ClientHandler
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println(LocalDateTime.now()+ ":客户端开始登录");

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("lxt");
        loginRequestPacket.setPassword("pwd");

        //编码
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(loginRequestPacket);
        // 写数据
        ctx.channel().writeAndFlush(byteBuf);

    }
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf byteBuf = (ByteBuf)msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if(loginResponsePacket.isSuccess()){
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println(LocalDateTime.now() + ":客户端登录成功！");
            }else {
                System.err.println(LocalDateTime.now() + ":客户端登录失败，原因："+loginResponsePacket.getReason());
            }
        }else if(packet instanceof MessageResponsePacket){
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket)packet;
            System.out.println(LocalDateTime.now()+":收到服务端消息："+messageResponsePacket.getMessage());

        }

    }
}
