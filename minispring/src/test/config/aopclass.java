package test.config;

import com.fzj.minispring.annotions.*;

import java.lang.reflect.Method;

/**
 * 切面配置类
 **/
//@Anno_fzjFlexibility
//@Anno_fzjAspect
//@Anno_fzjConfiguraton
public class aopclass {
    /**
     * 配置多个切点
     * ####################################
     * ####################################
     */
    /**
     * 配置service切点
     */
    @Anno_fzjPointcut("test.service")
    public void pointcutme() {
    }

    /**
     * service方法执行之前
     *
     * @param method      该方法
     * @param inputparams 输入参数
     */
    @Anno_fzjAspectBefore("pointcutme")
    public void aspectbeforeme(Method method, Object inputparams) {
        System.out.println("1111111111111" + method.getName());
    }

    /**
     * service方法执行之后
     *
     * @param method      该方法
     * @param inputparams 输入参数
     * @param outputparam 输出参数
     */
    @Anno_fzjAspectAfter("pointcutme")
    public void aspectaftereme(Method method, Object inputparams, Object outputparam) {
        System.out.println("1111111111111aspectaftereme");
    }

    /**
     * 配置controller切点
     */
    @Anno_fzjPointcut("test.controller")
    public void pointcutme1() {
    }

    /**
     * controller方法执行之前
     *
     * @param method
     * @param inputparams
     */
    @Anno_fzjAspectBefore("pointcutme1")
    public void aspectbeforeme1(Method method, Object inputparams) {
        System.out.println("1111111111111" + method.getName());
    }

    /**
     * controller方法执行之后
     *
     * @param method
     * @param inputparams
     * @param outputparam
     */
    @Anno_fzjAspectAfter("pointcutme1")
    public void aspectaftereme2(Method method, Object inputparams, Object outputparam) {
        System.out.println("1111111111111aspectaftereme1");
    }
}
