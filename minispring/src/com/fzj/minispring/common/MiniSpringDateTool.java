package com.fzj.minispring.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * @Author fzj
 * @Description //时间工具（公有）
 * @Date  14:05
 * @Param
 * @return
 **/
public class MiniSpringDateTool {
    /**
     * 获取当前时间
     *
     * @param pattern
     * @return
     */
    public static String getNow(String pattern) {
        SimpleDateFormat yyyyMMDDhhmmss = new SimpleDateFormat(pattern);
        return yyyyMMDDhhmmss.format(new Date());
    }

    public static String getNow(String pattern, Date d) {
        SimpleDateFormat yyyyMMDDhhmmss = new SimpleDateFormat(pattern);
        return yyyyMMDDhhmmss.format(d);
    }

    public static String yyyyMMddhhmmss() {
        return getNow("yyyy-MM-dd hh:mm:ss");
    }

    public static String yyyyMMdd() {
        return getNow("yyyy-MM-dd");
    }

    public static String yyyyMMddhhmmssOnlynum() {
        return getNow("yyyyMMddhhmmss");
    }

    public static String yyyyMMddOnlynum() {
        return getNow("yyyyMMdd");
    }

    public static String yyyyMMddOnlynum(Date d) {
        return getNow("yyyyMMdd", d);
    }
}
