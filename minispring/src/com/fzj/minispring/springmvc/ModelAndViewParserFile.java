package com.fzj.minispring.springmvc;

import java.io.OutputStream;

/**
 * 静态资源解析接口（受保护）
 */
interface ModelAndViewParserFile extends ModelAndViewParser {
    /**
     * 文件流输出
     *
     * @param outputStream
     * @throws Exception
     */
    void getReturn(OutputStream outputStream) throws Exception;
}
