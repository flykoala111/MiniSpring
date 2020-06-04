package com.fzj.minispring.common;

/**
 * 字符工具类（公有）
 **/
public class StringHelper {
    /**
     * 字符串拼接
     *
     * @param objects
     * @return
     */
    public static String combinString(Object... objects) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Object object : objects) {
            stringBuffer.append(object);
        }
        return stringBuffer.toString();
    }
}
