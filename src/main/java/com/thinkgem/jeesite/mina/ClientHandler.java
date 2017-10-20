package com.thinkgem.jeesite.mina;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ClientHandler extends IoHandlerAdapter {

    private static final Log log = LogFactory.getLog(ClientHandler.class);

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        log.debug("客户端收到消息: " + message.toString());
        super.messageReceived(session, message);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        log.debug("客户端消息发送成功: " + message.toString());
        super.messageSent(session, message);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.debug("客户端异常: " + cause.getMessage());
        super.exceptionCaught(session, cause);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        log.debug("客户端会话创建...");
        super.sessionCreated(session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.debug("客户端会话打开...");
        super.sessionOpened(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        log.debug("客户端会话关闭...");
        super.sessionClosed(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        log.debug("客户端会话休眠: " + session.getIdleCount(status));
        super.sessionIdle(session, status);
    }
}
