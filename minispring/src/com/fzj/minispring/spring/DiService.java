package com.fzj.minispring.spring;

import com.fzj.minispring.annotions.Anno_fzjAutowired;
import com.fzj.minispring.annotions.Anno_fzjConfiguraton;
import com.fzj.minispring.annotions.Anno_fzjProperties;
import com.fzj.minispring.exception.MiniSpringException;
import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;
import com.fzj.minispring.springaop.AopProxyFactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;

/**
 * 依赖注入（受保护）
 **/
class DiService {
    /**
     * @param @throws myException
     * @return void
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws
     * @Title: doAutowired
     * @Description: 设置autowired注入域(当类里的域被autowired注解修饰时 ， 将该域的值设为被注入类实例)
     */
    void doAutowired() throws MiniSpringException {
        if (GlobalParam.getIoc().isEmpty()) {
            return;
        }
        try {
            for (Map.Entry<String, Object> entry : GlobalParam
                    .getIoc().entrySet()) {
                Field[] fields = entry.getValue().getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (!field.isAnnotationPresent(Anno_fzjAutowired.class)) {
                        continue;
                    }
                    String beanname = field.getType().getName();
                    field.setAccessible(true);
                    field.set(entry.getValue(),
                            GlobalParam.getIoc(beanname));
                    new MiniSpringLog().printConsoleBlank(DiService.class, LogEnum.spring.getName("info"), " --doAutowired-- ", "Anno_fzjAutowired", "->", beanname);
                }
            }
        } catch (Exception e) {
            throw new MiniSpringException(0, LogEnum.spring.getName("exception"), "doAutowired", e.getMessage(), e.getStackTrace());
        }
    }

    /**
     * 将aop的注入域设置为代理类的实例
     *
     * @throws MiniSpringException
     */
    void doAopAutowired() throws MiniSpringException {
        if (GlobalParam.getIoc().isEmpty()) {
            return;
        }
        try {
            HashSet<String> aopset = GlobalParam.getAllAopClass();
            if (aopset.isEmpty()) {
                return;
            }
            for (Map.Entry<String, Object> entry : GlobalParam
                    .getIoc().entrySet()) {
                //切面类没有被注入到其他类
                if (aopset.contains(entry.getValue().getClass().getName())) {
                    GlobalParam.getIoc().put(entry.getValue().getClass().getName(), new AopProxyFactory(entry.getValue().getClass()).getAopProxyInstance());
                    new MiniSpringLog().printConsoleBlank(DiService.class, LogEnum.spring.getName("info"), " --doAopAutowired-- ", "Anno_fzjAutowired", "->", entry.getValue().getClass().getName());
                    continue;
                }
                Field[] fields = entry.getValue().getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (!field.isAnnotationPresent(Anno_fzjAutowired.class)) {
                        continue;
                    }
                    //切面类被注入了其他类
                    String beanname = field.getType().getName();
                    if (aopset.contains(beanname)) {
                        field.setAccessible(true);
                        Class targetcla = GlobalParam.getIoc(beanname).getClass();
                        field.set(entry.getValue(),
                                new AopProxyFactory(targetcla).getAopProxyInstance());
                        new MiniSpringLog().printConsoleBlank(DiService.class, LogEnum.spring.getName("info"), " --doAopAutowired-- ", "Anno_fzjAutowired", "->", beanname);
                    }
                }
            }
        } catch (Exception e) {
            throw new MiniSpringException(0, LogEnum.spring.getName("exception"), "doAutowired", e.getMessage(), e.getStackTrace());
        }
    }

    /**
     * 设置properties注入域
     *
     * @throws MiniSpringException
     */
    void doProperties() throws MiniSpringException {
        if (GlobalParam.getIoc().isEmpty()) {
            return;
        }
        try {
            for (Map.Entry<String, Object> entry : GlobalParam
                    .getIoc().entrySet()) {
                Class thi = entry.getValue().getClass();
                if (!thi.isAnnotationPresent(Anno_fzjConfiguraton.class)) {
                    continue;
                }
                Field[] fields = thi.getDeclaredFields();
                for (Field field : fields) {
                    if (!field.isAnnotationPresent(Anno_fzjProperties.class)) {
                        continue;
                    }
                    field.setAccessible(true);
                    field.set(entry.getValue(), GlobalParam.getProperties(field.getName()));
                    new MiniSpringLog().printConsoleBlank(DiService.class, LogEnum.spring.getName("info"), " --doProperties-- ", "Anno_fzjProperties", "->", thi.getName(), ".", field.getName());
                }
            }
        } catch (Exception e) {
            throw new MiniSpringException(0, LogEnum.spring.getName("exception"), "doProperties", e.getMessage(), e.getStackTrace());
        }
    }
}
