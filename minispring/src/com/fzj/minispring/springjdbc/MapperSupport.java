package com.fzj.minispring.springjdbc;

import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author fzj
 * @ClassName: MybatisMapperSupport
 * @Description: mapper接口代理方法处理器（受保护）
 * @date 2020年5月18日 上午11:49:51
 */
class MapperSupport implements InvocationHandler {
    private Class<?> objecta;

    MapperSupport(Class object1) {
        this.objecta = object1;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        new MiniSpringLog().printConsoleBlank(objecta, LogEnum.springjdbc.getName("debug"), objecta.getName(), ".", method.getName());
        // 调用数据库操作通用方法
        return JdbcExcuter.Excuter(objecta.getSimpleName(), method.getName(), args);
    }

}