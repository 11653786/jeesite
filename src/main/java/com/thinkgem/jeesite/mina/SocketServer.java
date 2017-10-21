package com.thinkgem.jeesite.mina;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8888);
            System.out.println("启动服务器....");
            Socket client = ss.accept();
            System.out.println("客户端:" + client.getInetAddress().getLocalHost() + ":" + client.getPort() + "已连接到服务器");

            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //读取客户端发送来的消息
            String mess = br.readLine();
            System.out.println("客户端：" + mess);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            bw.write(mess + "\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
