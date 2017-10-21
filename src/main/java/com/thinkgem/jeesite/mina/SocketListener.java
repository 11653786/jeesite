package com.thinkgem.jeesite.mina;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Administrator on 2017/10/21.
 */
public class SocketListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
                SocketServer.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


}
