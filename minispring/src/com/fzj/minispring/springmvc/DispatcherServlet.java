package com.fzj.minispring.springmvc;

import com.fzj.minispring.common.MethodTool;
import com.fzj.minispring.inteceptor.InteceptorHandler;
import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;
import com.fzj.minispring.spring.GlobalParam;
import freemarker.template.TemplateException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 中央处理器(公有)
 **/
public class DispatcherServlet extends HttpServlet implements DispatcherInterface {
    public DispatcherServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            doDispacher(req, resp);
        } catch (Exception e) {
            //500异常或者参数错误
            e.printStackTrace();
            try {
                new MiniSpringLog().printConsoleBlank(DispatcherServlet.class, LogEnum.springmvc.getName("debug"), "params wrong");
                StackTraceElement[] stackTraceElements = e.getStackTrace();
                StringBuffer errmsg = new StringBuffer("500 params wrong from fzjdispatcherservlet" + "\r\n");
                for (StackTraceElement s1 : stackTraceElements) {
                    errmsg.append(s1).append("\r\n");
                }
                resp.getWriter().write(errmsg.toString());
            } catch (IOException e1) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) {
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    /**
     * @param @param  req
     * @param @param  resp
     * @param @throws Exception
     * @return void
     * @throws
     * @Title: doDispacher
     * @Description: 请求分发
     */
    private void doDispacher(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, InvocationTargetException, IllegalAccessException, TemplateException {
        //设置请求和响应编码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

         String url = req.getRequestURI().substring(req.getContextPath().length(), req.getRequestURI().length());
        if (!GlobalParam.getMapping().containsKey(url) && !GlobalParam.getStaticResources().containsKey(url)) {
            //404未找到路径
            new MiniSpringLog().printConsoleBlank(DispatcherServlet.class, LogEnum.springmvc.getName("debug"), "url [", url, "] not found");
            resp.getWriter().write("404 not found from dispatcherservlet");
            return;
        }
        new MiniSpringLog().printConsoleBlank(DispatcherServlet.class, LogEnum.springmvc.getName("debug"), "found url [", url, "]");
        //////////拦截器处理
        if (!new InteceptorHandler(req, resp).isPassto()) {
            return;
        }
        /////////
        if (GlobalParam.getStaticResources().containsKey(url)) {
            /////////////////////////////////////////////////////是静态资源,返回文件内容或者写入outputstream流
            MAndV mAndV = new MAndV(url, resp);
            solveStaticresourcesRequest(mAndV);

        } else {
            /////////////////////////////////////////////////////不是静态资源(返回页面或者方法反射值)
            Method method = GlobalParam.getMapping(url);
            String beannamea = method.getDeclaringClass().getName();
            MAndV mv = new MAndV(beannamea, method, req, resp);
            solveControllerMethodRequest(mv);
        }

        //
    }

    @Override
    public void solveStaticresourcesRequest(MAndV mAndV) throws IOException, InvocationTargetException, IllegalAccessException {

        if (GlobalParam.getFilebuffersuffixs().containsKey(mAndV.getPath().substring(mAndV.getPath().lastIndexOf(".")))) {
            //字符流读取
            mAndV.getResponse().getWriter().print(new ModelAndViewParserFileImpl(mAndV).getReturn());
        } else if (GlobalParam.getFileStreamsuffixs().containsKey(mAndV.getPath().substring(mAndV.getPath().lastIndexOf(".")))) {
            //字节流读取
            new ModelAndViewParserFileImpl(mAndV).getReturn(mAndV.getResponse().getOutputStream());
        } else {
            //其他
            //字节流读取
            new ModelAndViewParserFileImpl(mAndV).getReturn(mAndV.getResponse().getOutputStream());
        }
    }

    @Override
    public void solveControllerMethodRequest(MAndV mAndV) throws IllegalAccessException, InvocationTargetException, IOException, TemplateException {
        if (mAndV.getMethod().getReturnType() == void.class) {
            //方法无返回值
            MethodTool.invokeMethod(mAndV);
        } else {
            //方法返回值为页面名称或者数据或者ModelandView对象
            mAndV.getResponse().setContentType("text/html");
            mAndV.getResponse().getWriter().print(new ModelAndViewParserDataImpl(mAndV).getReturn());
        }
    }
}
