package com.fzj.minispring.springmvc;


/**
 * 数据解析接口（解析页面，或者反射方法）(受保护)
 */
interface ModelAndViewParserData extends ModelAndViewParser {
    /**
     * 解析html文件
     *
     * @return
     * @throws Exception
     */
    Object parseViewHtml() throws Exception;

    /**
     * 解析ftl文件
     *
     * @return
     * @throws Exception
     */
    Object parseViewFtl() throws Exception;
}
