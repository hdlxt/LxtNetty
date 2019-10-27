package netty;

import common.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.time.LocalDate;
import java.util.Date;

/**
 * @ClassName: NettyClient
 * @Author: Administrator
 * @Description: netty 客户端
 * @Version: 1.0
 */
public class NettyClient  {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 客户端启动器
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new StringEncoder());
                    }
                });
        Channel channel = bootstrap
                .connect(Constants.DEFAULT_HOST,Constants.DEFAULT_PORT)
                .channel();
        while(true){
            channel.writeAndFlush(LocalDate.now()+":hello world!");
            Thread.sleep(2000);
        }
    }
}
