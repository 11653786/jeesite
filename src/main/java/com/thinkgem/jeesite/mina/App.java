package com.thinkgem.jeesite.mina;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;


/**
 * Hello world!
 */
public class App {

    private static final Log log = LogFactory.getLog(App.class);

    private static final String IP = "127.0.0.1";
    private static final int PORT = 8808;

    public static void main(String[] args) {
        log.debug("打开客户端...");

        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")))); //设置编码过滤器
        connector.setHandler(new ClientHandler());//设置事件处理器
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress(IP, PORT));//建立连接
        connectFuture.awaitUninterruptibly();//等待连接创建完成

        connectFuture.getSession().write("哈哈");//发送消息

//        connectFuture.getSession().close(true);
//        connectFuture.getSession().getCloseFuture().awaitUninterruptibly();//等待连接断开
//        connector.dispose();
    }
}
