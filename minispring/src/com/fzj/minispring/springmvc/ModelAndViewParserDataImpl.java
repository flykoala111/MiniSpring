package com.fzj.minispring.springmvc;

import com.fzj.minispring.annotions.Anno_fzjResponseBody;
import com.fzj.minispring.common.IoHelper;
import com.fzj.minispring.common.MethodTool;
import com.fzj.minispring.common.StringHelper;
import com.fzj.minispring.minienum.PageTemplateEnum;
import com.fzj.minispring.spring.GlobalParam;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * 视图解析器(受保护)
 **/
class ModelAndViewParserDataImpl extends ModelAndViewParserImpl implements ModelAndViewParserData {
    static final String pageLocation = "minispring.template.pagelocation";//页面位置key
    static final String targetTempfile = "ftltemplate.htm";//默认临时目标文件名称
    static final String templateSuffix = "minispring.template.suffix";//页面引擎的key

    MAndV mAndV;//解析器属性

    ModelAndViewParserDataImpl(MAndV mAndV) {
        this.mAndV = mAndV;
    }

    /**
     * 获取页面文件或者后台传输的数据
     *
     * @return
     * @throws Exception
     */
    @Override
    public File getFile() throws InvocationTargetException, IllegalAccessException {
        String homePath = this.mAndV.getRequest().getServletContext().getRealPath(GlobalParam.getProperties(pageLocation));
        Object page = MethodTool.invokeMethod(this.mAndV);
        if (this.mAndV.getMethod().getReturnType() == ModelandView.class) {
            //返回的是模型和视图
            ModelandView mv = ((ModelandView) page);
            this.mAndV.setData(mv.getData());
            String path = StringHelper.combinString(homePath, File.separator, mv.getPage(), GlobalParam.getProperties(templateSuffix));
            File pagefilefile = new File(path);
            return pagefilefile;
        } else {
            //只返回视图
            String path = StringHelper.combinString(homePath, File.separator, page, GlobalParam.getProperties(templateSuffix));
            File pagefilefile = new File(path);
            return pagefilefile;
        }

    }

    /**
     * 重写方法
     *
     * @return
     * @throws Exception
     */
    @Override
    public Object getReturn() throws IOException, TemplateException, IllegalAccessException, InvocationTargetException {
        Object object = null;
        if (this.mAndV.getMethod().isAnnotationPresent(Anno_fzjResponseBody.class)) {
            //有responsebody注解，返回方法的返回值
            object = transObject(MethodTool.invokeMethod(this.mAndV));
        } else {
            //只有mapping注解，返回解析过的视图
            String fileSuffix = GlobalParam.getProperties(templateSuffix).replace(".", "");//获取配置的页面后缀
            if (fileSuffix.equals(PageTemplateEnum.html.name())) {
                //页面引擎是html
                object = parseViewHtml();
            } else if (fileSuffix.equals(PageTemplateEnum.ftl.name())) {
                //页面引擎是freemarker
                object = parseViewFtl();
            } else {
                ///预留
            }
        }
        return object;
    }

    /**
     * 解析html页面
     *
     * @return
     * @throws Exception
     */
    @Override
    public Object parseViewHtml() throws IllegalAccessException, IOException, InvocationTargetException {
        return super.parseView();
    }

    /**
     * 解析ftl页面
     *
     * @return
     * @throws Exception
     */
    @Override
    public Object parseViewFtl() throws IOException, TemplateException, IllegalAccessException, InvocationTargetException {
        File file = new File(parseFtl());
        Object object = IoHelper.readFileAsStringbuffer(file);
        return object;
    }

    /**
     * freemarker解析ftl文件
     *
     * @return
     * @throws Exception
     */
    private String parseFtl() throws IOException, TemplateException, IllegalAccessException, InvocationTargetException {
        String targetHtmlPath = "";//目标文件路径
        File temFile = getFile();//源ftl文件
        String fileHomepath = temFile.getParent();//获取源文件所在文件夹
        String homePath = this.mAndV.getRequest().getServletContext().getRealPath(GlobalParam.getProperties(pageLocation));//配置的根路径
        Configuration configuration = new Configuration();//创建配置实例
        configuration.setDefaultEncoding("UTF-8");//设置编码
        configuration.setDirectoryForTemplateLoading(new File(fileHomepath));//加载模板文件
        targetHtmlPath = homePath + File.separator + targetTempfile;//目标文件（系统定义）
        Template template = configuration.getTemplate(temFile.getName());//获取模板
        File fileTarget = new File(targetHtmlPath);
        //创建目标文件
        if (!fileTarget.exists()) {
            fileTarget.createNewFile();
        }
        //将模板和数据模型合并生成静态页面
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileTarget), "UTF-8"));
        template.process(this.mAndV.getData(), out);
        //关闭流
        if (out != null) {
            out.flush();
            out.close();
        }
        return targetHtmlPath;
    }

    /**
     * 数据转换
     *
     * @param object
     * @return
     */
    private Object transObject(Object object) {
        if (object instanceof java.util.List ||
                object.getClass().isArray()
                ) {
            //数组或者集合
            object = JSONArray.fromObject(object);
        } else if (
            //四类八种基本类型
                object instanceof java.lang.String ||
                        object instanceof java.lang.Integer ||
                        object instanceof java.lang.Long ||
                        object instanceof java.lang.Float ||
                        object instanceof java.lang.Short ||
                        object instanceof java.lang.Double ||
                        object instanceof java.lang.Boolean ||
                        object instanceof java.lang.Byte
                ) {
            //不转换
        } else {
            //对象
            object = JSONObject.fromObject(object);
        }
        return object;
    }
}
