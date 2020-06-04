package com.fzj.minispring.springjdbc;

import com.fzj.minispring.common.ThreadTool;
import com.fzj.minispring.exception.MiniSpringException;
import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;
import com.fzj.minispring.spring.GlobalParam;

import java.util.LinkedList;
import java.util.List;

/**
 * springjdbc核心类（公有）
 **/
public class JdbcCore {
    private final String xmlLocationKey = "minispringjdbc.scanPackageXml";

    /**
     * 预加载类的无参构造器 可以在spring启动同时启动预加载类
     */
    public JdbcCore() {
        try {
            doScannerXml(GlobalParam.getProperties(xmlLocationKey));
            loadXml();
            ThreadTool.threadSleep(1000);
            new MiniSpringLog().printConsole(JdbcCore.class, LogEnum.springjdbc.getName("info"), "fzjspringJdbc init complated");
        } catch (MiniSpringException e) {
            e.printStackTrace();
        }
    }

    // 用于存放xml文件的全路径
    private List<String> ClassNames = new LinkedList<String>();


    /**
     * 扫描xml文件的包
     *
     * @param packagename
     */
    private void doScannerXml(String packagename) throws MiniSpringException {
        new ScannerXmlService().doScannerXml(packagename, ClassNames);
    }

    /**
     * 建立xml和接口方法的映射关系
     */
    private void loadXml() throws MiniSpringException {
        new ParseXmlService().loadXml(ClassNames);
    }

}
