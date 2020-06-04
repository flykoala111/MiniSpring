package com.fzj.minispring.springjdbc;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author fzj
 * @ClassName: MybatisMapperbeanFactory
 * @Description: mapper接口实例工厂（公有）
 * @date 2020年5月18日 上午11:52:19
 */
public class MapperbeanFactory {
    // 接口类
    private Class<?> mapperinterClass;

    public MapperbeanFactory(Class<?> t) {
        this.mapperinterClass = t;
    }

    public Object getMapperInstance() {
        // 获得mapper处理器的实例
        InvocationHandler handler1 = new MapperSupport(mapperinterClass);
        // 通过代理创建mapper接口的实例
        Object proxyObject = Proxy.newProxyInstance(mapperinterClass.getClassLoader(), new Class[]{mapperinterClass}, handler1);
        return proxyObject;
    }
}
