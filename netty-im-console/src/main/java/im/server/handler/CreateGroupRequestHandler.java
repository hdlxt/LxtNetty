package im.server.handler;

import im.protocol.request.CreateGroupRequestPacket;
import im.protocol.response.CreateGroupResponsePacket;
import im.session.Session;
import im.util.IDUtil;
import im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CreateGroupRequestHandler
 * @Author: lxt
 * @Description: 创建群聊处理器
 * @Version: 1.0
 */
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {



    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        List<String> userIdList = createGroupRequestPacket.getUserIdList();

        List<String> userNameList = new ArrayList<>();
        // 创建一个channel分组
        ChannelGroup channelGroup = new DefaultChannelGroup(channelHandlerContext.executor());

        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if(channel != null){
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        // 创建群聊创建结果响应
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(IDUtil.randomId());
        createGroupResponsePacket.setUserNameList(userNameList);

        // 给每个客户端发送拉群通知
        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.print("群创建成功，id为【"+createGroupResponsePacket.getGroupId()+"】，");
        System.out.println("群里面有："+createGroupResponsePacket.getUserNameList());

    }
}
