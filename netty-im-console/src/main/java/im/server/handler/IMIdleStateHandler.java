package im.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: IMIdleStateHandler
 * @Author: lxt
 * @Description:
 * @Version: 1.0
 */
public class IMIdleStateHandler extends IdleStateHandler {

    private static final int READER_IDLE_TIME = 15;


    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    public void channelIdle(ChannelHandlerContext channelHandlerContext, IdleStateEvent idleStateEvent){
        System.out.println(READER_IDLE_TIME+"秒内未读到数据，关闭连接");
        channelHandlerContext.channel().close();
    }

}
