package test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 *
 **/
public class test {
    public static void main(String []x) throws Exception{
        Class cla=Class.forName("test.controller.fzjspringtest");
        Method method=cla.getMethod("index", HttpServletRequest.class, HttpServletResponse.class, Map.class);
        Object o=method.invoke(cla.newInstance(),null,null,null);
        int a=1;
    }
}
