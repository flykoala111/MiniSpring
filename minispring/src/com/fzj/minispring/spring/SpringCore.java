package com.fzj.minispring.spring;

import com.fzj.minispring.common.ThreadTool;
import com.fzj.minispring.exception.MiniSpringException;
import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;
import com.fzj.minispring.springjdbc.JdbcCore;

import java.io.IOException;
import java.io.InputStream;

/**
 * minispring核心类（受保护）
 **/
class SpringCore implements Spring {
    private final String scanPackageKey = "minispring.scanPackage";//需要扫描的包的key
    private final String staticResourcesLocationKey = "minispring.staticresources.location";//静态资源位置的key
    private final String filestreamSuffixKey = "minispring.staticresources.filestreamsuffixs";//流读取后缀的key
    private final String filebufferSuffixKey = "minispring.staticresources.filebuffersuffixs";//字符读取后缀的key
    private String propertiesLocation;

    SpringCore(String location) {
        //构造器受保护
        this.propertiesLocation = location;
    }

    /**
     * 自定义spring框架初始化
     *
     * @throws MiniSpringException
     */
    @Override
    public void myInit() throws MiniSpringException {
        /**
         * ##########################################################minispring init#########################################################################################
         */
        GlobalParam.setLocation(propertiesLocation);//设置配置文件名称

        doLoadConfig(GlobalParam.getLocation());//加载配置文件

        doScanner(GlobalParam.getProperties(scanPackageKey));//扫描java类的包

        doScannerStaticResources(GlobalParam.getProperties(staticResourcesLocationKey));//扫描静态资源的包

        loadStaticResourcesSuffixs(GlobalParam.getProperties(filestreamSuffixKey), GlobalParam.getProperties(filebufferSuffixKey));//加载文件的读取方式

        /**
         * ##########################################################minispring core#######################################################################################
         */
        doInstance();//ioc容器的实例化
        doBean();//bean加载
        doDi();//设置注入域
        /**
         * #################################################################minispring webmvc##############################################################################
         */
        initHanlderMapping();//初始化处理器映射器
        /**
         * ###############################################################minispring aop###################################################################################
         */
        doAop();//加载aop

        doAopDi();//设置aop切面的注入域



        ThreadTool.threadSleep(1000);

        new MiniSpringLog().printConsole(SpringCore.class, LogEnum.spring.getName("info"), "fzjspring init complated");

        /**
         * #######################################################################minispring orm/jdbc############################################################################
         */
        loadJdbc();// 加载springjdbc
        /**
         * ######################################################################################################################################################
         */


    }


    /**
     * @param @param  location
     * @param @throws myException
     * @return void
     * @throws IOException
     * @throws
     * @Title: doLoadConfig
     * @Description: 加载配置文件
     */
    @Override
    public void doLoadConfig(String location) throws MiniSpringException {
        InputStream fis = null;
        try {
            fis = this.getClass().getClassLoader().getResourceAsStream(location.trim());
            GlobalParam.setProperties(fis);
            if (fis != null) {
                fis.close();
            }
        } catch (Exception e) {
            throw new MiniSpringException(0, LogEnum.spring.getName("exception"), "doLoadConfig", e.getMessage(), e.getStackTrace());
        }
    }

    /**
     * @param @param packagename
     * @return void
     * @throws
     * @Title: doScanner
     * @Description: 包扫描
     */
    @Override
    public void doScanner(String packagename) throws MiniSpringException {
        new ScannerService().doScanner(packagename);
    }

    /**
     * 静态资源读取方式加载
     */
    @Override
    public void loadStaticResourcesSuffixs(String sreamstr, String bufferstr) throws MiniSpringException {
        new ScannerService().loadStaticResourcesSuffixs(sreamstr, bufferstr);
    }

    /**
     * 静态资源扫描
     *
     * @param packagename
     * @throws MiniSpringException
     */
    @Override
    public void doScannerStaticResources(String packagename) throws MiniSpringException {
        new ScannerService().doScannerStaticResources(packagename);
    }

    /**
     * @param @throws myException
     * @return void
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws
     * @Title: doInstance
     * @Description: ioc容器初始化(将扫描包下的类以类名和类实例的方式添加到容器)
     */
    @Override
    public void doInstance() throws MiniSpringException {
        new IocService().doInstance();
    }

    /**
     * bean加载
     *
     * @throws MiniSpringException
     */
    @Override
    public void doBean() throws MiniSpringException {
        new LoadBeanService().loadBean();
    }

    /**
     * @param @throws myException
     * @return void
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws
     * @Title: doAutowired
     * @Description: 设置注入域(当类里的域被autowired或者properties注解修饰时 ， 将该域的值设为被注入类实例)
     */
    @Override
    public void doDi() throws MiniSpringException {
        DiService diService = new DiService();
        diService.doAutowired();
        diService.doProperties();
    }

    /**
     * aop配置加载
     *
     * @throws MiniSpringException
     */
    @Override
    public void doAop() throws MiniSpringException {
        AopService aopService = new AopService();
        aopService.doAopConfig();
        aopService.loadAopClass();
    }

    /**
     * 设置aop切面的注入域
     *
     * @throws MiniSpringException
     */
    @Override
    public void doAopDi() throws MiniSpringException {
        new DiService().doAopAutowired();
    }

    /**
     * @param
     * @return void
     * @throws
     * @Title: initHanlderMapping
     * @Description: 请求映射初始化(建立请求路径跟方法的对应关系)
     */
    @Override
    public void initHanlderMapping() throws MiniSpringException {
        new HanlderMappingService().initHanlderMapping();
    }

    /**
     * 加载jdbc
     */
    @Override
    public void loadJdbc() {
        new JdbcCore();
    }
}
