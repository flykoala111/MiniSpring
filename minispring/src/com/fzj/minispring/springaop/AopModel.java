package com.fzj.minispring.springaop;

/**
 * aop配置属性（公有）
 **/
public class AopModel {
    String classname;//类名
    String beforemethodname;//before方法
    String aftermethodname;//after方法
    String pointcut;//切点（例：test.service）
    String pointcutMethodname;//切点方法名

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getBeforemethodname() {
        return beforemethodname;
    }

    public void setBeforemethodname(String beforemethodname) {
        this.beforemethodname = beforemethodname;
    }

    public String getAftermethodname() {
        return aftermethodname;
    }

    public void setAftermethodname(String aftermethodname) {
        this.aftermethodname = aftermethodname;
    }

    public String getPointcut() {
        return pointcut;
    }

    public void setPointcut(String pointcut) {
        this.pointcut = pointcut;
    }

    public String getPointcutMethodname() {
        return pointcutMethodname;
    }

    public void setPointcutMethodname(String pointcutMethodname) {
        this.pointcutMethodname = pointcutMethodname;
    }
}
