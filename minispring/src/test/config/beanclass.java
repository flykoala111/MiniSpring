package test.config;

import com.fzj.minispring.annotions.Anno_fzjBean;
import com.fzj.minispring.annotions.Anno_fzjConfiguraton;
import com.fzj.minispring.annotions.Anno_fzjFlexibility;
import com.fzj.minispring.inteceptor.MiniSpringInteceptor;
import test.inteceptor.inter;

/**
 * bean配置
 **/
@Anno_fzjFlexibility
@Anno_fzjConfiguraton
public class beanclass {
    /**
     * 加载拦截器
     *
     * @return
     */
    @Anno_fzjBean
    public MiniSpringInteceptor inter() {
        return new inter();
    }
}
