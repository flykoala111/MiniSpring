package com.fzj.minispring.springjdbc;

import com.fzj.minispring.exception.MiniSpringException;
import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 *扫描xml文件的包（受保护）
 **/
class ScannerXmlService {
    /**
     * 扫描xml文件的包
     *
     * @param packagename
     */
    void doScannerXml(String packagename, List<String> ClassNames) throws MiniSpringException {
        try {
            URL url = this.getClass().getClassLoader().getResource(File.separator + packagename.replaceAll("\\.", "/"));
            File dir = new File(url.getFile());
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    doScannerXml(packagename + "." + file.getName(), ClassNames);
                } else {
                    if (file.getName().endsWith(".xml")) {
                        String path = url.getPath() + file.getName().trim();
                        ClassNames.add(path);
                        new MiniSpringLog().printConsoleBlank(ScannerXmlService.class, LogEnum.springjdbc.getName("info"), " --scann xml-- ", file.getName());
                    }
                }
            }
        } catch (Exception e) {
            throw new MiniSpringException(0, LogEnum.springjdbc.getName("exception"), "doScannerXml", e.getMessage(), e.getStackTrace());
        }
    }
}
