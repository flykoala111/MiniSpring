package com.fzj.minispring.inteceptor;

import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;
import com.fzj.minispring.spring.GlobalParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 拦截器处理器（公有）
 **/
public class InteceptorHandler {
    private static Class aClass;//拦截器的类
    private HttpServletRequest request;
    private HttpServletResponse response;

    public InteceptorHandler(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 走拦截规则
     *
     * @return
     * @throws Exception
     */
    public boolean isPassto() {
        boolean flg = false;
        try {
            String uri = this.request.getRequestURI().replace(this.request.getContextPath(), "");
            String[] uriar = uri.split("\\/");
            Class cl = getInterclass();
            if (cl == null) {
                //没有配置拦截器
                return true;
            }
            //请求路径是否在拦截器白名单
            Method method_noInterceptor = cl.getDeclaredMethod("noInterceptor");
            String[] noInterceptorUrl = (String[]) method_noInterceptor.invoke(cl.newInstance());//获取不进拦截器的集合
            for (String stringurl : noInterceptorUrl) {
                String[] stringurlar = stringurl.split("\\/");
                int resi = 0;
                for (int ii = 0; ii < stringurlar.length; ii++) {
                    if (stringurlar[ii].equals(uriar[ii])) {
                        resi++;
                    }
                }
                if (stringurlar.length == resi) {
                    return true;
                }
            }
            //走拦截方法
            Method method_isPass = cl.getDeclaredMethod("isPass", HttpServletRequest.class, HttpServletResponse.class);
            flg = (Boolean) method_isPass.invoke(cl.newInstance(), this.request, this.response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (flg) {
            new MiniSpringLog().printConsoleBlank(InteceptorHandler.class, LogEnum.inteceptor.getName("debug"), request.getRequestURI(), "pass through the inteceptor");
        } else {
            new MiniSpringLog().printConsoleBlank(InteceptorHandler.class, LogEnum.springmvc.getName("debug"), "no pass through the inteceptor");
        }
        return flg;
    }

    /**
     * 获取到定义的拦截器的类
     *
     * @return
     * @throws ClassNotFoundException
     */
    private Class getInterclass() {
        //单例获取拦截器的类
        if (aClass == null) {
            for (Map.Entry entry : GlobalParam.getIoc().entrySet()) {
                if (entry.getValue() instanceof MiniSpringInteceptor) {
                    aClass = entry.getValue().getClass();
                    break;
                }
            }
        }
        return aClass;
    }
}
