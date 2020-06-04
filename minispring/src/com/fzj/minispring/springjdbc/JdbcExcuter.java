package com.fzj.minispring.springjdbc;

import com.fzj.minispring.common.StringHelper;
import com.fzj.minispring.exception.MiniSpringException;
import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;
import com.fzj.minispring.spring.GlobalParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作执行器（受保护）
 **/
class JdbcExcuter {
    // 禁止通过new方法调用类
    private JdbcExcuter() {

    }

    /**
     * @param @param  targetname 目标名
     * @param @param  methodname 方法名
     * @param @param  args 参数
     * @param @return
     * @param @throws myException
     * @return Object
     * @throws
     * @Title: Excuter
     * @Description: 供外部调用的方法
     */
    static Object Excuter(String targetname, String methodname,
                          Object[] args) throws MiniSpringException {
        String sqlString = "";
        List<Map<String, Object>> sqlmap = GlobalParam.getSqlandMethod().get(
                targetname);
        if (sqlmap == null) {
            new MiniSpringLog().printException("getsqlmap error", "xml file targetname error:" + targetname);
            throw new MiniSpringException(0, LogEnum.springjdbc.getName("exception"), "Excuter error", null, null);
        }
        for (Map<String, Object> endmap : sqlmap) {
            if (endmap.get("id") != null) {
                if (endmap.get("id").equals(methodname)) {
                    sqlString = endmap.get("sql").toString();
                    break;
                }
            }
        }
        Map<String, List<Object>> mapres = operSql(sqlString,
                (Map<String, Object>) args[0]);
        if (sqlString.toLowerCase().startsWith("select")) {
            return JdbcCommonTool.sqlSelect(mapres.get("sql").get(0).toString(), mapres.get("objects"));
        } else {
            return JdbcCommonTool.sqlUpdate(mapres.get("sql").get(0).toString(), mapres.get("objects"));
        }
    }

    /**
     * 格式化sql和参数
     *
     * @param sqlString
     * @param sqlMap
     * @return
     */
    private static Map<String, List<Object>> operSql(String sqlString,
                                                     Map<String, Object> sqlMap) throws MiniSpringException {
        try {
            Map<String, List<Object>> mapres = new HashMap<String, List<Object>>();
            List<Integer> belist = splitBeandEn(sqlString, "#{");
            List<Integer> enlist = splitBeandEn(sqlString, "}");
            List<String> keyStrings = new ArrayList<String>();
            for (int i = 0; i < belist.size(); i++) {
                keyStrings.add(sqlString.substring(belist.get(i) + 2, enlist.get(i)).trim());
            }
            List<Object> objects = new ArrayList<Object>();
            List<Object> objects_a = new ArrayList<Object>();
            for (int i = 0; i < belist.size(); i++) {
                objects.add(sqlMap.get(keyStrings.get(i)));
                sqlString = sqlString.replace(StringHelper.combinString("#{", keyStrings.get(i), "}"), "?");
            }
            objects_a.add(sqlString);
            mapres.put("sql", objects_a);
            mapres.put("objects", objects);
            return mapres;
        } catch (Exception e) {
            new MiniSpringLog().printException("parse sql" + sqlString + "error");
            throw new MiniSpringException(0, LogEnum.springjdbc.getName("exception"), "Excuter error", e.getMessage(), e.getStackTrace());
        }
    }

    /**
     * 分割参数名称
     *
     * @param str
     * @param formatstr
     * @return
     */
    private static List<Integer> splitBeandEn(String str, String formatstr) {
        boolean flg = true;
        int index = 0;
        List<Integer> list = new ArrayList<Integer>();
        while (flg) {
            int a = str.indexOf(formatstr, index);
            if (a > 0) {
                list.add(a);
                index = a + 1;
            } else {
                break;
            }

        }
        return list;
    }
}
