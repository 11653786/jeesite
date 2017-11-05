package com.thinkgem.jeesite.mina;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Component
public class SocketServer {

    private static int port = 8888;

    private static final Log log = LogFactory.getLog(SocketServer.class);

    public static void main(String[] args) {
        getInstance();
    }


    public static void getInstance() {
        try {
            ServerSocket server = new ServerSocket(port);
            log.info("启动Socket: " + server.getInetAddress().getHostName() + ":" + server.getLocalPort());


            while (true) {
                Socket client = server.accept();
                if (client != null) {
                    log.info("客户端:" + client.getInetAddress().getHostAddress() + ":" + client.getPort() + "已连接到服务器");
                    receiveMessage(client);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void receiveMessage(Socket socket) throws IOException {
        String receive = "";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(64000);
        byte[] buffer = new byte[12800];
        int length;
        InputStream inputStream = socket.getInputStream();

        // read()会一直阻塞
        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
            // 转码后读取数据
            receive = byteArrayOutputStream.toString("UTF-8");
            log.info("客户端发送消息：" + receive);
            receive = receive.replace("Host:47.95.114.60", "");
            Map<String, Object> params = getMap(receive);
            //判断当前map
            if (params != null && params.size() > 0) {
                if (params.get("data").equals("0")) {  //首次注册
                    String cabinetNo = params.get("cabinetNo").toString();
                }else if(params.get("data").equals("1")){

                }
            }


            OutputStream socketWriter = socket.getOutputStream();
            socketWriter.write(receive.getBytes());
            socketWriter.flush();
        }


    }

    private class SocketThread extends Thread {

        private Socket client;

        public SocketThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {

            super.run();
        }
    }

    private static Map<String, Object> getMap(String receive) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (null != receive) {
            String[] param = receive.split("&");
            for (int i = 0; i < param.length; i++) {
                int index = param[i].indexOf('=');
                params.put(param[i].substring(0, index), param[i].substring((index + 1)));
            }
        }
        return params;
    }
}
