package com.fzj.minispring.spring;

import com.fzj.minispring.exception.MiniSpringException;

/**
 * minispring内核接口（受保护）
 */
interface Spring {
    /**
     * minispring框架初始化
     *
     * @throws MiniSpringException
     */
    void myInit() throws MiniSpringException;

    /**
     * 加载配置文件
     *
     * @param location
     * @throws MiniSpringException
     */
    void doLoadConfig(String location) throws MiniSpringException;

    /**
     * 包扫描
     *
     * @param packagename
     * @throws MiniSpringException
     */
    void doScanner(String packagename) throws MiniSpringException;

    /**
     * 静态资源读取方式加载
     *
     * @param sreamstr
     * @param bufferstr
     * @throws MiniSpringException
     */
    void loadStaticResourcesSuffixs(String sreamstr, String bufferstr) throws MiniSpringException;

    /**
     * 静态资源扫描
     *
     * @param packagename
     * @throws MiniSpringException
     */
    void doScannerStaticResources(String packagename) throws MiniSpringException;

    /**
     * ioc容器初始化(将扫描包下的类以类名和类实例的方式添加到容器)
     *
     * @throws MiniSpringException
     */
    void doInstance() throws MiniSpringException;

    /**
     * bean加载
     *
     * @throws MiniSpringException
     */
    void doBean() throws MiniSpringException;

    /**
     * 设置注入域(当类里的域被autowired或者properties注解修饰时 ， 将该域的值设为被注入类实例)
     *
     * @throws MiniSpringException
     */
    void doDi() throws MiniSpringException;

    /**
     * aop配置加载
     *
     * @throws MiniSpringException
     */
    void doAop() throws MiniSpringException;

    /**
     * 设置aop切面的注入域
     *
     * @throws MiniSpringException
     */
    public void doAopDi() throws MiniSpringException;

    /**
     * 请求映射初始化(建立请求路径跟方法的对应关系)
     *
     * @throws MiniSpringException
     */
    void initHanlderMapping() throws MiniSpringException;

    /**
     * 加载jdbc
     */
    void loadJdbc();
}
