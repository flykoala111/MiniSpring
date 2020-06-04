package com.fzj.minispring.springjdbc;

import cc.julong.fzj.FzjFileOperation;
import com.fzj.minispring.exception.MiniSpringException;
import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;
import com.fzj.minispring.spring.GlobalParam;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *xml解析（受保护）
 **/
class ParseXmlService {
    /**
     * 建立xml和mapper的映射关系
     */
    void loadXml(List<String> ClassNames) throws MiniSpringException {
        try {
            for (String xmlpath : ClassNames) {
                File xFile = new File(xmlpath);
                List<Map<String, Object>> xmlres = FzjFileOperation
                        .readXML_DOM4J(xFile);

                String targetname = xFile.getName().replace(".xml", "");
                if (GlobalParam.getInterandMethod().containsKey(xmlres.get(0).get("targetname"))) {
                    GlobalParam.getSqlandMethod().put(targetname, xmlres);
                    new MiniSpringLog().printConsoleBlank(ParseXmlService.class, LogEnum.springjdbc.getName("info"), " --parse xml-- ", xmlpath.substring(xmlpath.lastIndexOf("/")).replace("/", ""));
                }
            }
        } catch (Exception e) {
            throw new MiniSpringException(0, LogEnum.springjdbc.getName("exception"), "loadXml", e.getMessage(), e.getStackTrace());
        }
    }
}
