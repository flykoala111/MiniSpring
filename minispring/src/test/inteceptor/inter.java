package test.inteceptor;

import com.fzj.minispring.inteceptor.MiniSpringInteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * 须实现MiniSpringInteceptor接口
 **/
public class inter implements MiniSpringInteceptor {
    @Override
    public String[] noInterceptor() {
        //配置拦截器白名单
        return new String[]{"/test/", "/utils/"};
    }

    @Override
    public boolean isPass(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //为不在白名单的路径定义拦截规则[true:通过拦截规则;false:不通过]
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;utf-8");
        response.getWriter().write("此请求被拦截器拦截！");
        return false;
    }
}
