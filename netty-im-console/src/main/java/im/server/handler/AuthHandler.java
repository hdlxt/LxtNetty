package im.server.handler;

import im.util.LoginUtil;
import im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;

/**
 * @ClassName: AuthHandler
 * @Description: to do
 * @Author: lxt
 * @Date: 2019-10-30 13:00
 * @Version 1.0
 **/
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(LocalDateTime.now()+":收到客户端登录请求");
        if(!SessionUtil.hasLogin(ctx.channel())){
            ctx.channel().close();
        }else{
            // 移除
            ctx.pipeline().remove(this);
            super.channelRead(ctx,msg);
        }
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}
