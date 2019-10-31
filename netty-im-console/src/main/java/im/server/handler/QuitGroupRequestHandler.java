package im.server.handler;

import im.protocol.request.QuitGroupRequestPacket;
import im.protocol.response.QuitGroupResponsePacket;
import im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @ClassName: QuitGroupHandler
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();


    private QuitGroupRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {
        String groupId = quitGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(channelHandlerContext);

        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        quitGroupResponsePacket.setGroupId(quitGroupRequestPacket.getGroupId());
        quitGroupResponsePacket.setSuccess(true);
        channelHandlerContext.channel().writeAndFlush(quitGroupResponsePacket);
    }
}
