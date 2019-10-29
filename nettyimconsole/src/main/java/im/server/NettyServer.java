package im.server;

import common.Constants;
import im.codec.PacketDecoder;
import im.codec.PacketEncoder;
import im.server.handler.LoginRequestHandler;
import im.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @ClassName: NettyServer
 * @Author: lxt
 * @Description: netty服务端
 * @Version: 1.0
 */
public class NettyServer {
    public static void main(String[] args) {
        // 主线程池，接受客户端连接请求 创建新连接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 从线程池，工作线程 实际io操作等
        NioEventLoopGroup work = new NioEventLoopGroup();
        // 服务端启动器
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss,work)
                // 设置channel（相当于nio中的ServerSocketChannel）
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        nioSocketChannel.pipeline().addLast(new LoginRequestHandler());
                        nioSocketChannel.pipeline().addLast(new MessageRequestHandler());
                        nioSocketChannel.pipeline().addLast(new PacketEncoder());
                    }
                });
        // 绑定默认端口
//        serverBootstrap.bind(Constants.DEFAULT_PORT);
        // 自动绑定递增端口测试
        bind(serverBootstrap, Constants.DEFAULT_PORT);
    }

    /**
     * 自动绑定递增端口测试
     *  绑定失败，依次递增直到绑定成功为止
     * @param serverBootstrap
     * @param port
     */
    private static void bind(final ServerBootstrap serverBootstrap,final int port){
        serverBootstrap.bind(port)
                .addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if(future.isSuccess()){
                            System.out.println("端口【"+port+"】绑定成功！");
                        }else{
                            System.err.println("端口【"+port+"】绑定失败！");
                            bind(serverBootstrap,port+1);
                        }
                    }
                });
    }
}
