package com.fzj.minispring.spring;

import com.fzj.minispring.annotions.Anno_fzjBean;
import com.fzj.minispring.annotions.Anno_fzjConfiguraton;
import com.fzj.minispring.exception.MiniSpringException;
import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * bean加载（受保护）
 **/
class LoadBeanService {
    void loadBean() throws MiniSpringException {
        if (GlobalParam.getIoc().isEmpty()) {
            return;
        }
        try {
            for (Map.Entry<String, Object> entry : GlobalParam
                    .getIoc().entrySet()) {
                Class class11 = entry.getValue().getClass();
                if (!class11.isAnnotationPresent(Anno_fzjConfiguraton.class)) {
                    continue;
                }
                Method[] methods = class11.getDeclaredMethods();
                for (Method method1 : methods) {
                    if (!method1.isAnnotationPresent(Anno_fzjBean.class)) {
                        continue;
                    }
                    Object clainstance = method1.invoke(entry.getValue().getClass().newInstance());
                    GlobalParam.setIoc(clainstance.getClass().getName(), clainstance);
                    new MiniSpringLog().printConsoleBlank(IocService.class, LogEnum.spring.getName("info"), " --loadBean-- ", "Anno_fzjBean", "->", clainstance.getClass().getName());
                }
            }
        } catch (Exception e) {
            throw new MiniSpringException(0, LogEnum.spring.getName("exception"), "loadBean", e.getMessage(), e.getStackTrace());
        }
    }
}
