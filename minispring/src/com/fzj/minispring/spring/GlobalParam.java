package com.fzj.minispring.spring;

import cc.julong.fzj.FzjFormValidation;
import com.fzj.minispring.common.MiniSpringSecurity;
import com.fzj.minispring.common.StringHelper;
import com.fzj.minispring.exception.MiniSpringException;
import com.fzj.minispring.minienum.LogEnum;
import com.fzj.minispring.springaop.AopModel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 容器参数（公有）
 **/
public class GlobalParam {
    private static final String propertiesName = "miniSpringPropertiesLocation";//webxml里配置文件的属性名称
    private static final String defaultPropertiesLocation = "minispring.properties";//默认配置文件名

    // 全局容器（用于存放扫描过的类名）
    private static List<String> ClassNames = new LinkedList<String>();
    // 路径映射器（用于存放映射和方法的关系）
    private static Map<String, Method> HandlerMappings = new ConcurrentHashMap<String, Method>(1);
    // Ioc容器（用于存放类名和对应的类的实例）
    private static Map<String, Object> Ioc = new ConcurrentHashMap<String, Object>(1);
    // sql容器（存放sql语句和对应接口名的集合）
    private static Map<String, List<Map<String, Object>>> SqlandMethod = new ConcurrentHashMap<String, List<Map<String, Object>>>();
    // 配置文件名称
    private static String PropertiesLocation = defaultPropertiesLocation;//默认文件名
    // 用于存放配置文件属性的域
    private static Properties P = new Properties();
    // 存放mapper接口数据
    private static Map<String, Method[]> InterandMethod = new ConcurrentHashMap<String, Method[]>();
    //存放静态资源信息
    private static Map<String, String> StaticResources = new ConcurrentHashMap<String, String>(1);
    // 字符流方式读取的文件后缀
    private static Map<String, Object> Filebuffersuffixs = new ConcurrentHashMap<String, Object>(1);
    // 字节流方式读取的文件后缀
    private static Map<String, Object> FileStreamsuffixs = new ConcurrentHashMap<String, Object>(1);
    //切面的配置
    private static Map<String, AopModel> AopConfigurations = new ConcurrentHashMap<String, AopModel>(1);
    //切面类容器
    private static Map<String, List<String>> AopClass = new ConcurrentHashMap<String, List<String>>(1);


    /**
     * ---------------------------------------------------------------------------------------------------
     */

    protected static Map<String, List<String>> getAopClass() {
        return AopClass;
    }

    //获取所有类名
    protected static HashSet<String> getAllAopClass() {
        HashSet hashSet = new LinkedHashSet();
        for (Map.Entry entry : AopClass.entrySet()) {
            List<String> listclassname = (List<String>) entry.getValue();
            for (String string : listclassname) {
                hashSet.add(string);
            }
        }
        return hashSet;
    }

    //添加aop类
    protected static void setAopClass(String packagename, String classname) {
        if (AopClass.containsKey(packagename)) {
            List<String> classnames = AopClass.get(packagename);
            classnames.add(classname);
            AopClass.put(packagename, classnames);

        } else {
            List<String> classnames = new ArrayList<String>(1);
            classnames.add(classname);
            AopClass.put(packagename, classnames);
        }
    }

    public static AopModel getAopModel(String classname) {
        //获取此类的aop配置
        String packagename = "";
        boolean flg = false;
        for (Map.Entry entry : AopClass.entrySet()) {
            List<String> list1 = (List) entry.getValue();
            for (String str : list1) {
                if (classname.equals(str)) {
                    packagename = String.valueOf(entry.getKey());
                    flg = true;
                    break;
                }
            }
            if (flg) {
                break;
            }
        }
        for (Map.Entry entry : AopConfigurations.entrySet()) {
            AopModel aopModel = (AopModel) entry.getValue();
            if (packagename.equals(aopModel.getPointcut())) {
                return aopModel;
            }
        }
        return null;
    }

    public static Map<String, AopModel> getAopConfigurations() {
        return AopConfigurations;
    }

    protected static void setAopConfigurations(String key1, AopModel aopModel) {
        AopConfigurations.put(key1, aopModel);
    }

    public static String getPropertiesName() {
        return propertiesName;
    }

    public static Map<String, Object> getFilebuffersuffixs() {
        return Filebuffersuffixs;
    }


    public static Map<String, Object> getFileStreamsuffixs() {
        return FileStreamsuffixs;
    }

    protected static void setFilebuffersuffixs(Map<String, Object> filebuffersuffixs) {
        Filebuffersuffixs = filebuffersuffixs;
    }

    protected static void setFileStreamsuffixs(Map<String, Object> fileStreamsuffixs) {
        FileStreamsuffixs = fileStreamsuffixs;
    }

    public static Map<String, String> getStaticResources() {
        return StaticResources;
    }

    protected static void setStaticResources(Map<String, String> staticResources) {
        StaticResources = staticResources;
    }

    public static Map<String, List<Map<String, Object>>> getSqlandMethod() {
        return SqlandMethod;
    }

    public static Map<String, Method[]> getInterandMethod() {
        return InterandMethod;
    }

    public static void addInterandMethod(String key, Method[] value) {
        InterandMethod.put(key, value);
    }

    protected static List<String> getClassNames() {
        return ClassNames;
    }

    public static Map<String, Method> getMapping() {
        return HandlerMappings;
    }

    public static Method getMapping(String key) {
        return HandlerMappings.get(key);
    }

    protected static void setMapping(String key, Method value) throws MiniSpringException {
        if (!HandlerMappings.containsKey(key)) {
            HandlerMappings.put(key, value);

        } else {
            throw new MiniSpringException(0, LogEnum.spring.getName("exception"), "setMapping", StringHelper.combinString("mapping ", key, " already exist"), null);
        }

    }

    public static Map<String, Object> getIoc() {
        return Ioc;
    }

    public static Object getIoc(String key) {
        return Ioc.get(key);
    }

    protected static void setIoc(String key, Object value) throws MiniSpringException {
        if (!Ioc.containsKey(key)) {
            Ioc.put(key, value);
        } else {
            throw new MiniSpringException(0, LogEnum.spring.getName("exception"), "setIoc", "the bean " + key + " alraady exists", null);
        }
    }

    public static String getLocation() {
        return PropertiesLocation;
    }

    protected static void setLocation(String location) {
        if (FzjFormValidation.isNotNullOrEmpty(location)) {
            PropertiesLocation = location;
        }
    }

    protected static void setProperties(InputStream fis) throws IOException {
        P.load(fis);
    }

    public static String getProperties(String key) {
        String str = P.getProperty(key);
        if (str.endsWith(")") && str.startsWith("MINISPRING(")) {
            str = str.substring(11, str.length() - 1);
            //解密被加密过的字符串
            str = MiniSpringSecurity.stringDecode(str);
        }
        return str;
    }
}
