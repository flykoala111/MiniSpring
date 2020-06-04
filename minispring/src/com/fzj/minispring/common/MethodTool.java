package com.fzj.minispring.common;

import cc.julong.fzj.FzjReceiveParams;
import com.fzj.minispring.spring.GlobalParam;
import com.fzj.minispring.springmvc.MAndV;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 方法反射工具类（公有）
 **/
public class MethodTool {
    /**
     * 获取方法的反射值(通过方法的输入参数类型组合输入参数)
     *
     * @param mv
     * @return
     * @throws Exception
     */
    public static Object invokeMethod(MAndV mv) throws IllegalAccessException, InvocationTargetException {
        List<Object> listparam = new ArrayList<>(1);
        Class[] paratypes = mv.getMethod().getParameterTypes();
        for (Class cla : paratypes) {
            if (cla == HttpServletRequest.class) {
                listparam.add(mv.getRequest());
            } else if (cla == HttpServletResponse.class) {
                listparam.add(mv.getResponse());
            } else if (cla == Map.class) {
                listparam.add(FzjReceiveParams.loadParamAsMap(mv.getRequest()));
            }
        }
        Object[] objects = new Object[listparam.size()];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = listparam.get(i);
        }
        return mv.getMethod().invoke(GlobalParam.getIoc(mv.getPath()), objects);
    }
}
