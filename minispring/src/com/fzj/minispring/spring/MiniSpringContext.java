package com.fzj.minispring.spring;

import com.fzj.minispring.exception.MiniSpringException;

/**
 * minispring全局类（公有）
 **/
public class MiniSpringContext {
    public MiniSpringContext() {
        //公共构造器
    }

    /**
     * 获取bean实例(提供给外部的方法)     *
     *
     * @param t
     * @return
     */
    public Object getBean(Class t) {
        return GlobalParam.getIoc(t.getName());
    }

    public Object getBean(String classname) {
        return GlobalParam.getIoc(classname);
    }

    /**
     * 重新加载
     *
     * @param paoperties
     * @throws MiniSpringException
     */
    public void restartMiniSpring(String paoperties) throws MiniSpringException {
        new SpringCore(paoperties).myInit();
    }
}
