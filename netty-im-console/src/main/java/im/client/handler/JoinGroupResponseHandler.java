package im.client.handler;

import im.protocol.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName: JoinGroupResponseHandler
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupResponsePacket joinGroupResponsePacket) throws Exception {
        if(joinGroupResponsePacket.isSuccess()){
            System.out.println("加入【"+joinGroupResponsePacket.getGroupId()+"】群聊成功");

        }else{
            System.err.println("加入【"+joinGroupResponsePacket.getGroupId()+"】群聊失败，原因=>"+joinGroupResponsePacket.getReason());
        }
    }
}
