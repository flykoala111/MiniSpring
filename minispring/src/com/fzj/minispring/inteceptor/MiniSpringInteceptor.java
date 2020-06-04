package com.fzj.minispring.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器接口（提供给外部继承的接口）（公有）
 **/
public interface MiniSpringInteceptor {
    String[] noInterceptor() throws Exception;//不被拦截的url集合

    boolean isPass(HttpServletRequest request, HttpServletResponse response) throws Exception;//请求是否通过
}
