package im.server.handler;

import im.protocol.request.JoinGroupRequestPacgket;
import im.protocol.response.JoinGroupResponsePacket;
import im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @ClassName: JoinsGroupRequestHandler
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacgket> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();


    private JoinGroupRequestHandler() {

    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupRequestPacgket joinGroupRequestPacgket) throws Exception {
        // 获取对应群聊的channelGroup 然后将当前用户添加进去
        String groupId = joinGroupRequestPacgket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.add(channelHandlerContext.channel());
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        joinGroupResponsePacket.setSuccess(true);
        joinGroupResponsePacket.setGroupId(groupId);
        channelHandlerContext.channel().writeAndFlush(joinGroupResponsePacket);
    }
}
