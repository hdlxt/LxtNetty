package netty;

import common.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: NettyClient
 * @Author: lxt
 * @Description: netty 客户端
 * @Version: 1.0
 */
public class NettyTestClient {

    private static final  int MAX_RETRY = 5;

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 客户端启动器
        Bootstrap bootstrap = new Bootstrap();
        // 指定线程模型
        bootstrap.group(group)
                // 指定io类型
                .channel(NioSocketChannel.class)
                /**
                 *      option() 方法可以给连接设置一些 TCP 底层相关的属性，比如上面，我们设置了三种 TCP 属性，其中
                 *
                 *      ChannelOption.CONNECT_TIMEOUT_MILLIS 表示连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
                 *      ChannelOption.SO_KEEPALIVE 表示是否开启 TCP 底层心跳机制，true 为开启
                 *      ChannelOption.TCP_NODELAY 表示是否开始 Nagle 算法，true 表示关闭，false 表示开启，通俗地说，如果
                 * 要求高实时性，有数据发送时就马上发送，就设置为 true 关闭，如果需要减少发送次数减少网络交互，就设
                 * 置为 false 开启
                 */
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                // 指定处理逻辑
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
//                      channel.pipeline().addLast(new StringEncoder());
                        // 添加逻辑处理器
                        channel.pipeline().addLast(new FirstClientHandler());


                    }
                });
//        connect(bootstrap,"juejin.im",80,MAX_RETRY);
        connect(bootstrap,Constants.DEFAULT_HOST,Constants.DEFAULT_PORT,MAX_RETRY);
//        ChannelFuture channelFuture = bootstrap
//                .connect(Constants.DEFAULT_HOST,Constants.DEFAULT_PORT)
//                .addListener(future -> {
//                    if(future.isSuccess()){
//                        System.out.println("连接成功！");
//                    }else{
//                        System.err.println("连接失败");
//                        // 重新连接操作
//                    }
//                });
//        if(channelFuture.isSuccess()){
//            Channel channel = channelFuture.channel();
//            while(true){
//                channel.writeAndFlush(LocalDate.now()+":hello world!");
//                Thread.sleep(2000);
//            }
//        }
    }

    /**
     *  重连
     * @param bootstrap
     * @param host
     * @param port
     * @param retry
     */
    private static void connect(Bootstrap bootstrap,String host,int port,int retry){
        bootstrap.connect(host,port)
                .addListener(future -> {
                    if(future.isSuccess()){
                        System.out.println("连接成功！");
                    }else if(retry == 0){
                        System.err.println("重试次数已用完，放弃连接！");
                    }else{
                        // 重新连接操作
                        int order = (MAX_RETRY - retry) + 1;
                        // 本次重连间隔
                        int deplay = 1 << order;
                        System.err.println(LocalDateTime.now() + ":连接失败，第"+order+"次重连...");
                        /**
                         *      bootstrap.config() 这个方法返回的是 BootstrapConfig，他是对 Bootstrap 配置参数的抽象
                         * ，然后 bootstrap.config().group() 返回的就是我们在一开始的时候配置的线程模型 workerGroup
                         * ，调 workerGroup 的 schedule 方法即可实现定时任务逻辑。
                         */
                        bootstrap.config().group()
                                .schedule(()-> connect(bootstrap,host,port,retry - 1),deplay, TimeUnit.SECONDS);
                    }
                });
    }
}
