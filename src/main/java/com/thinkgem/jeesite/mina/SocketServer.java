package com.thinkgem.jeesite.mina;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private static int port=8888;

    private static final Log log = LogFactory.getLog(SocketServer.class);

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8888);
            log.info("启动Socket: "+server.getInetAddress().getHostAddress()+":"+server.getLocalPort());


            while (true) {
                Socket client = server.accept();
                if(client!=null){
                    log.info("客户端:" + client.getInetAddress().getLocalHost() + ":" + client.getPort() + "已连接到服务器");
                    //读取客户端发送来的消息
                    BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String mess = br.readLine();
                    log.info("客户端发来的消息：" + mess);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                    bw.write(mess + "\n");
                    log.info("服务返回消息：" + mess);
                    bw.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
