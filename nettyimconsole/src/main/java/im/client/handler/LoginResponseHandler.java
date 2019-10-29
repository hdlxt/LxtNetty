package im.client.handler;

import im.protocol.request.LoginRequestPacket;
import im.protocol.response.LoginResponsePacket;
import im.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @ClassName: LoginResponseHandler
 * @Author: lxt
 * @Description: 登录响应处理器
 * @Version: 1.0
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {


    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println(LocalDateTime.now()+ ":客户端开始登录");

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("lxt");
        loginRequestPacket.setPassword("pwd");

        // 写数据
        ctx.channel().writeAndFlush(loginRequestPacket);

    }
    @Override
    public void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket){
            if(loginResponsePacket.isSuccess()){
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println(LocalDateTime.now() + ":客户端登录成功！");
            }else {
                System.err.println(LocalDateTime.now() + ":客户端登录失败，原因："+loginResponsePacket.getReason());
            }
    }
}
