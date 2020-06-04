package com.fzj.minispring.spring;

import com.fzj.minispring.annotions.Anno_fzjController;
import com.fzj.minispring.annotions.Anno_fzjRequestMapping;
import com.fzj.minispring.common.StringHelper;
import com.fzj.minispring.exception.MiniSpringException;
import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 路径映射器（受保护）
 **/
class HanlderMappingService {
    /**
     * @param
     * @return void
     * @throws
     * @Title: initHanlderMapping
     * @Description: 请求映射初始化(建立请求路径跟方法的对应关系)
     */
    void initHanlderMapping() throws MiniSpringException {
        if (GlobalParam.getIoc().isEmpty()) {
            return;
        }
        try {
            for (Map.Entry<String, Object> entry : GlobalParam.getIoc().entrySet()) {
                Class<?> classzz = entry.getValue().getClass();
                if (!classzz.isAnnotationPresent(Anno_fzjController.class)) {
                    continue;
                }
                // 根映射
                String baseurl = "";
                if (classzz.isAnnotationPresent(Anno_fzjRequestMapping.class)) {
                    Anno_fzjRequestMapping requestmapping = classzz.getAnnotation(Anno_fzjRequestMapping.class);
                    baseurl = requestmapping.value();
                }
                Method methods[] = classzz.getMethods();
                for (Method method : methods) {
                    if (!method.isAnnotationPresent(Anno_fzjRequestMapping.class)) {
                        continue;
                    }
                    // 方法映射
                    Anno_fzjRequestMapping requestmapping = method
                            .getAnnotation(Anno_fzjRequestMapping.class);
                    String url = ("/" + baseurl + "/" + requestmapping.value()).replaceAll("/+", "/");
                    GlobalParam.setMapping(url, method);
                    new MiniSpringLog().printConsoleBlank(HanlderMappingService.class, LogEnum.spring.getName("info"), " --initHanlderMapping-- ", url, "->", method.getName());
                }
            }
        } catch (Exception e) {
            throw new MiniSpringException(0, LogEnum.spring.getName("exception"), "initHanlderMapping", e.getMessage(), e.getStackTrace());
        }
    }
}
