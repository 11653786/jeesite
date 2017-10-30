package com.thinkgem.jeesite.mina;

import com.thinkgem.jeesite.common.utils.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.net.InetSocketAddress;
import java.util.Date;


public class ServerHandler extends IoHandlerAdapter {

    private static final Log log = LogFactory.getLog(ServerHandler.class);

    /**
     * mina服务器接收消息的方法
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String content = message.toString();
        //获取客户端信息
        InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
        //柜子端口号
        Integer port = address.getPort();
        //柜子ip
        String ip = address.getAddress().getHostAddress();
        String cabinetNo = ip + ":" + port;
        log.debug("柜子ip:" + cabinetNo + "服务端接收到的数据为: " + content);
//        content = content.trim();
//        if (content.equalsIgnoreCase("login")) {
//
//        } else if (content.equalsIgnoreCase("logout")) {
//            session.close(true);
//            return;
//        }
//        //回复服务器接收到的时间
        session.write(content);
        super.messageReceived(session, message);
        //保存客户端的会话session
        SessionMap sessionMap = SessionMap.newInstance();
        sessionMap.addSession("1", session);
    }


    /**
     * 服务器发送消息
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        log.debug("服务端发送信息成功: " + message.toString());
        super.messageSent(session, message);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.debug("服务端发送异常: " + cause.getMessage());
        super.exceptionCaught(session, cause);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        log.debug("服务端与客户端创建连接...");
        super.sessionCreated(session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.debug("服务端与客户端连接打开...");
        super.sessionOpened(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        log.debug("服务端与客户端连接关闭...");
        super.sessionClosed(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        log.debug("服务端进入空闲状态: " + session.getIdleCount(status));
        super.sessionIdle(session, status);
    }
}
