package com.fzj.minispring.springmvc;

import cc.julong.fzj.FzjReceiveParams;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 解析器属性(公有)
 **/
public class MAndV {
    private String path;//类名或者文件名

    private Method method;//路径方法

    private HttpServletRequest request;//请求

    private HttpServletResponse response;//响应

    private Map<String, Object> params;//输入参数(由页面传过来的参数)

    private Object data;//后台设置的数据（用于显示到页面）

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    protected MAndV(String url, Method method, HttpServletRequest request, HttpServletResponse response) {
        this.path = url;
        this.method = method;
        this.request = request;
        this.response = response;
        this.params = FzjReceiveParams.loadParamAsMap(request);
    }

    protected MAndV(String url, HttpServletResponse response) {
        this.path = url;
        this.response = response;
    }


    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
