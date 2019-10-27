package io;

import common.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName: IOServer
 * @Author: Administrator
 * @Description: bio服务端
 * @Version: 1.0
 */
public class IOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Constants.DEFAULT_PORT);
        // 接收新连接的县城
        new Thread(()->{
            while (true){
                try {
                    // 阻塞方法获取新连接
                    Socket socket = serverSocket.accept();
                    // 每一个新连接创建一个线程 负责读取数据
                    new Thread(()->{
                        try{
                            int len;
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            // 按字节流方式读取数据
                            while((len = inputStream.read(data)) != -1){
                                System.out.println(new String(data,0,len));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
