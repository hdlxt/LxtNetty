package io;

import common.Constants;

import java.net.Socket;
import java.time.LocalDate;

/**
 * @ClassName: IOClient
 * @Author: lxt
 * @Description: bio客户端
 * @Version: 1.0
 */
public class IOClient {
    public static void main(String[] args) {
        new Thread(()->{
            try {
                Socket socket = new Socket(Constants.DEFAULT_HOST,Constants.DEFAULT_PORT);
                while (true){
                    socket.getOutputStream().write((LocalDate.now() +": hello world").getBytes());
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
