package com.fzj.minispring.springmvc;

/**
 * 中央处理器接口(受保护)
 **/
interface DispatcherInterface {
    /**
     * 处理静态资源请求
     *
     * @param mAndV
     * @throws Exception
     */
    void solveStaticresourcesRequest(MAndV mAndV) throws Exception;


    /**
     * 处理路径方法请求
     *
     * @param mAndV
     * @throws Exception
     */
    void solveControllerMethodRequest(MAndV mAndV) throws Exception;
}
