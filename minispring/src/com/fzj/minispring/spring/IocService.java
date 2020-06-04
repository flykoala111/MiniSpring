package com.fzj.minispring.spring;

import cc.julong.fzj.FzjFormValidation;
import com.fzj.minispring.annotions.Anno_fzjController;
import com.fzj.minispring.annotions.Anno_fzjFlexibility;
import com.fzj.minispring.annotions.Anno_fzjMapper;
import com.fzj.minispring.annotions.Anno_fzjService;
import com.fzj.minispring.exception.MiniSpringException;
import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;
import com.fzj.minispring.springjdbc.MapperbeanFactory;

/**
 * ioc操作（受保护）
 **/
class IocService {
    /**
     * @param @throws myException
     * @return void
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws
     * @Title: doInstance
     * @Description: ioc容器初始化(将扫描包下的类以类名和类实例的方式添加到容器)
     */
    void doInstance() throws MiniSpringException {
        try {
            if (GlobalParam.getClassNames().size() == 0) {
                //没有扫描到类
                return;
            }
            for (String classname : GlobalParam.getClassNames()) {
                if (!FzjFormValidation.isNotNullOrEmpty(classname)) {
                    continue;
                }
                Class<?> classzz = Class.forName(classname);
                String beanname = classname;
                if (classzz.isAnnotationPresent(Anno_fzjFlexibility.class)) {
                    // 被flexibility注解修饰
                    GlobalParam.setIoc(beanname, classzz.newInstance());
                    new MiniSpringLog().printConsoleBlank(IocService.class, LogEnum.spring.getName("info"), " --doInstance-- ", "Anno_fzjFlexibility", "->", classname);
                } else if (classzz.isAnnotationPresent(Anno_fzjController.class)) {
                    // 被controller注解修饰
                    GlobalParam.setIoc(beanname, classzz.newInstance());
                    new MiniSpringLog().printConsoleBlank(IocService.class, LogEnum.spring.getName("info"), " --doInstance-- ", "Anno_fzjController", "->", classname);
                } else if (classzz.isAnnotationPresent(Anno_fzjService.class)) {
                    // 被service注解修饰
                    GlobalParam.setIoc(beanname, classzz.newInstance());
                    new MiniSpringLog().printConsoleBlank(IocService.class, LogEnum.spring.getName("info"), " --doInstance-- ", "Anno_fzjService", "->", classname);
                } else if (classzz.isAnnotationPresent(Anno_fzjMapper.class)) {
                    // 被mapper注解修饰 将mapper的代理类实例放入ioc容器
                    GlobalParam.setIoc(beanname, new MapperbeanFactory(classzz).getMapperInstance());
                    // 接口方法集合和接口类全名存入集合
                    GlobalParam.getInterandMethod().put(beanname, classzz.getDeclaredMethods());
                    new MiniSpringLog().printConsoleBlank(IocService.class, LogEnum.spring.getName("info"), " --doInstance-- ", "Anno_fzjMapper", "->", classname);
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            //ioc容器初始化异常
            throw new MiniSpringException(0, LogEnum.spring.getName("exception"), "doInstance", e.getMessage(), e.getStackTrace());
        }
    }
}
