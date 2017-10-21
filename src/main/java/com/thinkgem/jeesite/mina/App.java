package com.thinkgem.jeesite.mina;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.session.IoSession;
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

    private static final String IP = "localhost";
    private static final int PORT = 8888;

    public static void main(String[] args) {
        log.debug("打开客户端...");

        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")))); //设置编码过滤器
        connector.setHandler(new ClientHandler());//设置事件处理器
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress(IP, PORT));//建立连接
        connectFuture.awaitUninterruptibly();//等待连接创建完成

        IoSession session = connectFuture.getSession();
        session.write("哈哈woaile");//发送消息
        ReadFuture readFuture = session.read();
        Object Message = readFuture.getMessage();
        session.close(true);
//        session.getCloseFuture().awaitUninterruptibly();//等待连接断开
//        connectFuture.getSession().close(true);
//        connectFuture.getSession().getCloseFuture().awaitUninterruptibly();//等待连接断开
//        connector.dispose();
    }
}
