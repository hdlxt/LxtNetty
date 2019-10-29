package im.client;

import common.Constants;
import im.client.handler.LoginResponseHandler;
import im.client.handler.MessageResponseHandler;
import im.codec.PacketDecoder;
import im.codec.PacketEncoder;
import im.protocol.request.MessageRequestPacket;
import im.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: NettyClient
 * @Author: lxt
 * @Description: netty 客户端
 * @Version: 1.0
 */
public class NettyClient {

    private static final  int MAX_RETRY = 5;

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 客户端启动器
        Bootstrap bootstrap = new Bootstrap();
        // 指定线程模型
        bootstrap.group(group)
                // 指定io类型
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                // 指定处理逻辑
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        // 添加逻辑处理器
                        channel.pipeline().addLast(new PacketDecoder());
                        channel.pipeline().addLast(new LoginResponseHandler());
                        channel.pipeline().addLast(new MessageResponseHandler());
                        channel.pipeline().addLast(new PacketEncoder());


                    }
                });
        connect(bootstrap,Constants.DEFAULT_HOST,Constants.DEFAULT_PORT,MAX_RETRY);
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
                        System.out.println("连接成功！启动控制台线程。。。");
                        Channel channel = ((ChannelFuture)future).channel();
                        // 连接成功，启动控制台线程
                        startConsoleThread(channel);
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


    private static void startConsoleThread(Channel channel){
        new Thread(()->{
            while(!Thread.interrupted()){
                if(LoginUtil.hasLogin(channel)){
                    System.out.println("输入消息发送至服务端");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(line);
                    channel.writeAndFlush(packet);
                }
            }
        }).start();

    }
}
