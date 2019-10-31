package im.server.handler;

import im.protocol.request.GroupMessageRequestPacket;
import im.protocol.response.GroupMessageResponsePacket;
import im.session.Session;
import im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();


    private GroupMessageRequestHandler() {

    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {
        String groupId = groupMessageRequestPacket.getToGroupId();
        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setFromGroupId(groupId);
        groupMessageResponsePacket.setMessage(groupMessageRequestPacket.getMessage());
        groupMessageResponsePacket.setFromUser(SessionUtil.getSession(channelHandlerContext.channel()));

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.writeAndFlush(groupMessageResponsePacket);
    }
}
