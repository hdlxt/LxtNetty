package im.client.handler;

import im.protocol.request.LoginRequestPacket;
import im.protocol.response.LoginResponsePacket;
import im.session.Session;
import im.util.LoginUtil;
import im.util.SessionUtil;
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
    public void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket){
        String userId = loginResponsePacket.getUserId();
        String userName = loginResponsePacket.getUserName();
            if(loginResponsePacket.isSuccess()){
//                LoginUtil.markAsLogin(ctx.channel());
                SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
//                System.out.println(LocalDateTime.now() + ":客户端登录成功！");
                System.out.println("[" + userName + "]登录成功，userId 为: " + loginResponsePacket.getUserId());
            }else {
                System.err.println(LocalDateTime.now() + userName+":客户端登录失败，原因："+loginResponsePacket.getReason());
            }
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭");
    }
}
