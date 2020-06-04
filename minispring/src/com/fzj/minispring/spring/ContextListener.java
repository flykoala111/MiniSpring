package com.fzj.minispring.spring;

import com.fzj.minispring.exception.MiniSpringException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * servlet监听，tomcat启动时执行（公有）
 **/
public class ContextListener extends HttpServlet implements
        ServletContextListener {
    /**
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void contextDestroyed(ServletContextEvent arg0) {
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            // 加载minispring
            new SpringCore(servletContextEvent.getServletContext().getInitParameter(GlobalParam.getPropertiesName())).myInit();
        } catch (MiniSpringException e) {
            e.printStackTrace();
        }
    }
}

