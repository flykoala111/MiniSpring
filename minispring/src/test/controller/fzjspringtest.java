package test.controller;

import com.fzj.minispring.annotions.Anno_fzjAutowired;
import com.fzj.minispring.annotions.Anno_fzjController;
import com.fzj.minispring.annotions.Anno_fzjRequestMapping;
import com.fzj.minispring.annotions.Anno_fzjResponseBody;
import com.fzj.minispring.springmvc.ModelandView;
import test.config.Properties;
import test.mapper.xmltest;
import test.service.fzjspringtestservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author fzj
 * @ClassName: fzjspringtest
 * @Description: 控制器示例
 * @date 2020年5月18日 下午1:08:50
 */
@Anno_fzjController//控制器注解
@Anno_fzjRequestMapping("test")//路径父映射
public class fzjspringtest {
    @Anno_fzjAutowired
    fzjspringtestservice fzjsprin;//service注入
    @Anno_fzjAutowired
    xmltest xmltest;//mapper注入
    @Anno_fzjAutowired
    Properties properties;//配置文件service注入

    /**
     * 返回主页面
     *
     * @return
     * @throws Exception
     */
    @Anno_fzjRequestMapping("index")
    public ModelandView index(HttpServletRequest re) throws Exception {
        String a = fzjsprin.aoptest("yyy", "ddd", re);
        Map<String, Object> maphead = new HashMap<>(1);
        maphead.put("projectname", properties.ProjectName);
        maphead.put("projectversion", properties.ProjectVersion);
        maphead.put("author", properties.Author);
        Map<String, Object> mapall = new HashMap<>(1);
        mapall.put("modules", getListmaps("modules"));
        mapall.put("functions", getListmaps("functions"));
        mapall.put("head", maphead);
        return new ModelandView("index", mapall);
    }

    /**
     * 返回controller页面
     *
     * @return
     */
    @Anno_fzjRequestMapping("controllerindex")
    public String getController() {
        return "func/controllerindex";
    }

    /**
     * 返回service页面
     *
     * @return
     */
    @Anno_fzjRequestMapping("serviceindex")
    public String getServiceindex() {
        return "func/serviceindex";
    }

    /**
     * 返回mapper页面
     *
     * @return
     */
    @Anno_fzjRequestMapping("mapperindex")
    public String getMapperindex() {
        return "func/mapperindex";
    }

    /**
     * 返回xml页面
     *
     * @return
     */
    @Anno_fzjRequestMapping("xmlindex")
    public String getXmlindex() {
        return "func/xmlindex";
    }

    /**
     * 导出数据库结构文档
     *
     * @param resp
     * @param a
     */
    @Anno_fzjRequestMapping("export")//路径子映射
    public void exportdocofdbstruct(HttpServletResponse resp, Map<String, Object> a) {
        try {
            fzjsprin.exe(resp, a);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 返回数据
     *
     * @return
     */
    @Anno_fzjRequestMapping("getdata")
    @Anno_fzjResponseBody
    public Map<String, Object> getdata() {
        Map<String, Object> mapres = new HashMap<>(1);
        List<Map<String, Object>> liatmap = xmltest.sel(null);
        int i = 1;
        for (Map<String, Object> map1 : liatmap) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            if (map1.get("ardate") != null) {
                try {
                    map1.put("ardate", sdf.format((Date) map1.get("ardate")));
                } catch (Exception e) {
                    e.printStackTrace();
                    map1.put("ardate", "");
                }
            }
            map1.put("index", i);
            i++;

        }
        mapres.put("data", liatmap);
        mapres.put("headname", "我是MiniSpring框架");
        return mapres;
    }

    /**
     * 获得模块或功能数据
     *
     * @param type
     * @return
     */
    private List<Map<String, Object>> getListmaps(String type) {
        Map<String, Object> mapin = new HashMap<>(1);
        mapin.put("type", type);
        List<Map<String, Object>> listall = fzjsprin.selminimodules(mapin);
        for (int i = 1; i <= listall.size(); i++) {
            if (i % 3 == 0) {
                listall.get(i - 1).put("index", true);
            } else {
                listall.get(i - 1).put("index", false);
            }
        }
        return listall;
    }

    /**
     * 获取功能模块数据
     *
     * @param request
     * @param map1
     * @return
     * @throws Exception
     */
    @Anno_fzjRequestMapping("getfunpagedata")
    @Anno_fzjResponseBody
    public List<Map<String, Object>> getfunpagedata(HttpServletRequest request, Map<String, Object> map1) throws Exception {
        List<Map<String, Object>> listmap = new ArrayList<>(1);
        String page = map1.get("page").toString();
        switch (page) {
            case "controller":
                break;
            case "service":
                break;
            case "mapper":
                break;
            case "xml":
                break;
            default:
                break;
        }
        return listmap;
    }

    /**
     * 源码下载
     *
     * @param response
     */
    @Anno_fzjRequestMapping("downloadproject")
    public void downloadproject(HttpServletResponse response, HttpServletRequest request) {
        InputStream is = null;
        OutputStream os = null;
        try {
            String name = request.getParameter("name") + ".rar";
            String filename = URLEncoder.encode("MiniSpring源码", "utf-8") + ".rar";
            // 禁止浏览器缓存
            response.setHeader("Cache-Control", "no-store"); // 禁止浏览器缓存
            response.setHeader("Pragrma", "no-cache"); // 禁止浏览器缓存
            response.setDateHeader("Expires", 0); // 禁止浏览器缓存
            response.setHeader("Content-disposition", "attachment; filename=" + filename);
            response.setContentType("application/octet-stream;Charset=utf-8");
            is = request.getServletContext().getResourceAsStream("/WEB-INF/resources/" + name);
            os = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int offset = 0;
            while ((offset = is.read(bytes, 0, bytes.length)) != -1) {
                os.write(bytes, 0, offset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
