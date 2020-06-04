package com.fzj.minispring.common;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 数据安全（公有）
 **/
public class MiniSpringSecurity {
    /**
     * 加密
     *
     * @param i$
     * @return
     */
    public static String stringEncode(String i$) {
        return Base64.getEncoder().encodeToString(i$.getBytes());
    }

    /**
     * 解密
     *
     * @param i$
     * @return
     */
    public static String stringDecode(String i$) {
        try {
            return new String(Base64.getDecoder().decode(i$), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
