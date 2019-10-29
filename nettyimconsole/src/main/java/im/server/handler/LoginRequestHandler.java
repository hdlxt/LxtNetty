package im.server.handler;

import im.protocol.request.LoginRequestPacket;
import im.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @ClassName: LoginRequestHandler
 * @Author: lxt
 * @Description: 登录请求处理器
 * @Version: 1.0
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(LocalDateTime.now()+"收到客户端请求："+loginRequestPacket);
        // 登录逻辑
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        // 登录校验
        if(vaild(loginRequestPacket)){
            loginResponsePacket.setSuccess(true);
        }else{
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("帐号密码校验失败");
        }
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }
    private boolean vaild(LoginRequestPacket loginRequestPacket){
        return true;
    }
}
