package com.fzj.minispring.springmvc;


/**
 * 视图解析接口(受保护)
 **/
interface ModelAndViewParser {
    /**
     * 文件解析
     */
    Object parseView() throws Exception;

    /**
     * 获得解析内容
     *
     * @return
     * @throws Exception
     */
    Object getReturn() throws Exception;


}
