package netty;

import common.Constants;
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
public class NettyTestServer {
    public static void main(String[] args) {
        /**
         *      首先看到，我们创建了两个NioEventLoopGroup，这两个对象可以看做是传统IO编程模型的两大线程组，bossGroup表示监听端口
         * ，accept 新连接的线程组，workerGroup表示处理每一条连接的数据读写的线程组，不理解的同学可以看一下上一小节《Netty是什么》
         * 。用生活中的例子来讲就是，一个工厂要运作，必然要有一个老板负责从外面接活，然后有很多员工，负责具体干活，老板就是bossGroup
         * ，员工们就是workerGroup，bossGroup接收完连接，扔给workerGroup去处理。
         *      接下来 我们创建了一个引导类 ServerBootstrap，这个类将引导我们进行服务端的启动工作，直接new出来开搞。
         * 我们通过.group(bossGroup, workerGroup)给引导类配置两大线程组，这个引导类的线程模型也就定型了。
         *      然后，我们指定我们服务端的 IO 模型为NIO，我们通过.channel(NioServerSocketChannel.class)来指定 IO 模型，当然，
         * 这里也有其他的选择，如果你想指定 IO 模型为 BIO，那么这里配置上OioServerSocketChannel.class类型即可，当然通常我
         * 们也不会这么做，因为Netty的优势就在于NIO。
         *      接着，我们调用childHandler()方法，给这个引导类创建一个ChannelInitializer，这里主要就是定义后续每条连接的数据读写
         * ，业务处理逻辑，不理解没关系，在后面我们会详细分析。ChannelInitializer这个类中，我们注意到有一个泛型参数NioSocketChannel
         * ，这个类呢，就是 Netty 对 NIO 类型的连接的抽象，而我们前面NioServerSocketChannel也是对 NIO 类型的连接的抽象
         * ，NioServerSocketChannel和NioSocketChannel的概念可以和 BIO 编程模型中的ServerSocket以及Socket两个概念对应上
         */
        // 主线程池，接受客户端连接请求 创建新连接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 从线程池，工作线程 实际io操作等
        NioEventLoopGroup work = new NioEventLoopGroup();
        // 服务端启动器
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss,work)
                // 设置channel（相当于nio中的ServerSocketChannel）
                .channel(NioServerSocketChannel.class)
                /**
                 * 给服务端channel设置一些属性，最常见的就是so_backlog
                 * 表示系统用于临时存放已完成三次握手的请求的队列的最大长度
                 * ，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                 */
                .option(ChannelOption.SO_BACKLOG,1024)
                /**
                 *      childOption()可以给每条连接设置一些TCP底层相关的属性，
                 * 比如上面，我们设置了两种TCP属性，其中
                 *      ChannelOption.SO_KEEPALIVE表示是否开启TCP底层心跳机制，true为开启
                 *      ChannelOption.TCP_NODELAY表示是否开启Nagle算法，true表示关闭
                 * ，false表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送
                 * ，就关闭，如果需要减少发送次数减少网络交互，就开启。
                 */
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new FirstServerHandler());
//                        nioSocketChannel.pipeline().addLast(new StringDecoder());
//                        nioSocketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
//                            @Override
//                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
//                                System.out.println(s);
//                            }
//                        });
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
