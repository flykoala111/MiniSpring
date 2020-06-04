package com.fzj.minispring.spring;

import com.fzj.minispring.annotions.*;
import com.fzj.minispring.common.StringHelper;
import com.fzj.minispring.exception.MiniSpringException;
import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;
import com.fzj.minispring.springaop.AopModel;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * aop加载（受保护）
 **/
class AopService {
    /**
     * 加载aop配置
     *
     * @throws MiniSpringException
     */
    void doAopConfig() throws MiniSpringException {
        try {
            if (GlobalParam.getIoc().isEmpty()) {
                return;
            }
            for (Map.Entry<String, Object> entry : GlobalParam
                    .getIoc().entrySet()) {
                Class cl = entry.getValue().getClass();
                if (!cl.isAnnotationPresent(Anno_fzjAspect.class)) {
                    //不是aop类
                    continue;
                }
                if (!cl.isAnnotationPresent(Anno_fzjConfiguraton.class)) {
                    //不是配置类
                    continue;
                }
                Method[] methods = cl.getDeclaredMethods();
                for (Method method1 : methods) {
                    if (method1.isAnnotationPresent(Anno_fzjPointcut.class)) {
                        //该方法配置切点
                        String annotationValue = method1.getAnnotation(Anno_fzjPointcut.class).value();
                        AopModel aopModel = new AopModel();
                        aopModel.setClassname(cl.getName());
                        aopModel.setPointcut(annotationValue);
                        GlobalParam.setAopConfigurations(method1.getName(), aopModel);
                        new MiniSpringLog().printConsoleBlank(AopService.class, LogEnum.aop.getName("info"), " --doAopConfig pointcut-- ", "pointcut->", annotationValue, ";", "pointcutmethod->", method1.getName());
                    }
                }
                for (Method method1 : methods) {
                    if (method1.isAnnotationPresent(Anno_fzjAspectBefore.class)) {
                        //该方法方法执行前的操作
                        String annotationValue = method1.getAnnotation(Anno_fzjAspectBefore.class).value();
                        if (GlobalParam.getAopConfigurations().containsKey(annotationValue)) {
                            AopModel aopModel = GlobalParam.getAopConfigurations().get(annotationValue);
                            aopModel.setBeforemethodname(method1.getName());
                            GlobalParam.setAopConfigurations(annotationValue, aopModel);
                            new MiniSpringLog().printConsoleBlank(AopService.class, LogEnum.aop.getName("info"), " --doAopConfig methodbefore-- ", "pointcutmethod->", annotationValue, ";", "methodbefore->", method1.getName());
                        }
                    }
                    if (method1.isAnnotationPresent(Anno_fzjAspectAfter.class)) {
                        //该方法方法执行后的操作
                        String annotationValue = method1.getAnnotation(Anno_fzjAspectAfter.class).value();
                        if (GlobalParam.getAopConfigurations().containsKey(annotationValue)) {
                            AopModel aopModel = GlobalParam.getAopConfigurations().get(annotationValue);
                            aopModel.setAftermethodname(method1.getName());
                            GlobalParam.setAopConfigurations(annotationValue, aopModel);
                            new MiniSpringLog().printConsoleBlank(AopService.class, LogEnum.aop.getName("info"), " --doAopConfig methodafter-- ", "pointcutmethod->", annotationValue, ";", "methodafter->", method1.getName());
                        }
                    }
                }
                //

            }
        } catch (Exception e) {
            throw new MiniSpringException(0, LogEnum.aop.getName("exception"), "doAopConfig", e.getMessage(), e.getStackTrace());
        }

    }

    private List<String> allAopClassNames = new ArrayList<>(1);//所有的aop类名

    /**
     * 扫描aop的类
     */
    void loadAopClass() throws MiniSpringException {
        try {
            if (GlobalParam.getAopConfigurations().isEmpty()) {
                //没有配置aop
                return;
            }
            for (Map.Entry entry : GlobalParam.getAopConfigurations().entrySet()) {
                String packagename = ((AopModel) entry.getValue()).getPointcut();
                doScannerAop(packagename);
            }
        } catch (Exception e) {
            throw new MiniSpringException(0, LogEnum.aop.getName("exception"), "loadAopClass", e.getMessage(), e.getStackTrace());
        }
    }

    /**
     * 扫描aop的包
     *
     * @param packagename
     */
    private void doScannerAop(String packagename) {
        URL url = this.getClass().getClassLoader()
                .getResource(File.separator + packagename.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                doScannerAop(packagename + "." + file.getName());
            } else {
                if (!".class".equals(file.getName().substring(file.getName().lastIndexOf(".")))) {
                    continue;
                }
                GlobalParam.setAopClass(packagename, StringHelper.combinString(packagename, ".", file.getName().replace(".class", "").trim()));
                new MiniSpringLog().printConsoleBlank(AopService.class, LogEnum.aop.getName("info"), " --doScannerAop-- ", packagename, ".", file.getName().replace(".class", "").trim());
            }
        }
    }
}
