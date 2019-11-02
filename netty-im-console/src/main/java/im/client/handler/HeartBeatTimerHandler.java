package im.client.handler;

import im.protocol.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: HeartBeatTimerHandler
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {
    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);
        super.channelActive(ctx);
    }


    private void scheduleSendHeartBeat(ChannelHandlerContext channelHandlerContext){
        channelHandlerContext.executor().schedule(()->{
            if(channelHandlerContext.channel().isActive()){
                channelHandlerContext.channel().writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeat(channelHandlerContext);
            }
        },HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
