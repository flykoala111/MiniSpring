package com.fzj.minispring.springaop;

import com.fzj.minispring.spring.GlobalParam;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * aop代理类工厂（公有）
 **/
public class AopProxyFactory implements MethodInterceptor, Serializable {
    /**
     * 目标类
     */
    private Class tarclass;

    public AopProxyFactory(Class tarclass) {
        this.tarclass = tarclass;
    }

    /**
     * 回调方法
     *
     * @param o
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        doAop("before", method, objects, null);//寻找该切点的before方法并执行
        Object o1 = method.invoke(tarclass.newInstance(), objects);
        doAop("after", method, objects, o1);//寻找该切点的after方法并执行
        return o1;
    }

    /**
     * 获取代理类的实例
     *
     * @return
     */
    public Object getAopProxyInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.tarclass);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    /**
     * 执行切点的before或者after方法
     *
     * @param meflg      方法标识
     * @param methodfrom 方法
     * @param objects    输入参数
     * @param object     返回值
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void doAop(String meflg, Method methodfrom, Object[] objects, Object object) throws IllegalAccessException, InvocationTargetException {
        String thisclass = tarclass.getName();//获取到aop本类类名
        AopModel aopModel = GlobalParam.getAopModel(thisclass);//获取到此类的aop配置
        Object configclassinstance = GlobalParam.getIoc(aopModel.getClassname());//获取到配置类的实例
        Method method = null;
        switch (meflg) {
            case "before":
                for (Method method1 : configclassinstance.getClass().getDeclaredMethods()) {
                    if (aopModel.getBeforemethodname().equals(method1.getName())) {
                        method = method1;
                        break;
                    }
                }
                if (method != null) {
                    method.invoke(configclassinstance, methodfrom, (Object) objects);
                }
                break;
            case "after":
                for (Method method1 : configclassinstance.getClass().getDeclaredMethods()) {
                    if (aopModel.getAftermethodname().equals(method1.getName())) {
                        method = method1;
                        break;
                    }
                }
                if (method != null) {
                    method.invoke(configclassinstance, methodfrom, (Object) objects, object);
                }
                break;
            default:
                break;
        }

    }
}
