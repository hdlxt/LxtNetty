package im.client.handler;

import im.protocol.response.GroupMessageResponsePacket;
import im.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName: GroupMessageResponseHandler
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupMessageResponsePacket groupMessageResponsePacket) throws Exception {
        String fromGroupId = groupMessageResponsePacket.getFromGroupId();
        Session fromUser = groupMessageResponsePacket.getFromUser();
        System.out.println("收到群[" + fromGroupId + "]中[" + fromUser + "]发来的消息：" + groupMessageResponsePacket.getMessage());
    }
}
