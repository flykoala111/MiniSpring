package test.controller;

import com.fzj.minispring.annotions.Anno_fzjController;
import com.fzj.minispring.annotions.Anno_fzjRequestMapping;
import com.fzj.minispring.annotions.Anno_fzjResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 *
 **/
@Anno_fzjController
@Anno_fzjRequestMapping("test2")
public class test2 {
    @Anno_fzjRequestMapping("geturl")
    @Anno_fzjResponseBody
    public String geturl(HttpServletRequest request) {
        return "你的全路径是" + request.getRequestURL();
    }
}
