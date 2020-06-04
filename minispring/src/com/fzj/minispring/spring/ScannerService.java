package com.fzj.minispring.spring;

import com.fzj.minispring.common.StringHelper;
import com.fzj.minispring.exception.MiniSpringException;
import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;
import net.sf.json.JSONArray;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 类，静态资源扫描（受保护）
 **/
class ScannerService {
    /**
     * @param @param packagename
     * @return void
     * @throws
     * @Title: doScanner
     * @Description: 包扫描
     */
    void doScanner(String packagename) throws MiniSpringException {
        try {
            URL url = this.getClass().getClassLoader()
                    .getResource(File.separator + packagename.replaceAll("\\.", "/"));
            File dir = new File(url.getFile());
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    doScanner(packagename + "." + file.getName());
                } else {
                    if (!".class".equals(file.getName().substring(file.getName().lastIndexOf(".")))) {
                        continue;
                    }
                    GlobalParam.getClassNames().add(StringHelper.combinString(packagename, ".", file.getName().replace(".class", "").trim()));
                    new MiniSpringLog().printConsoleBlank(ScannerService.class, LogEnum.spring.getName("info"), " --doScanner-- ", packagename, ".", file.getName().replace(".class", "").trim());
                }
            }
        } catch (Exception e) {
            throw new MiniSpringException(0, LogEnum.spring.getName("exception"), "doScanner", e.getMessage(), e.getStackTrace());
        }
    }

    /**
     * 静态资源扫描
     *
     * @param packagename
     * @throws MiniSpringException
     */

    void doScannerStaticResources(String packagename) throws MiniSpringException {
        try {
            URL url = this.getClass().getClassLoader().getResource("");
            String homepath = url.getPath().substring(0, url.getPath().lastIndexOf("WEB-INF/"));
            String path = StringHelper.combinString(homepath, packagename, "/");
            File dir = new File(path);
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    doScannerStaticResources(packagename + "/" + file.getName());
                } else {
                    String fpath = StringHelper.combinString(packagename, "/", file.getName().trim());
                    GlobalParam.getStaticResources().put(fpath, path + file.getName().trim());
                    new MiniSpringLog().printConsoleBlank(ScannerService.class, LogEnum.spring.getName("info"), " --doScannerStaticResources-- ", fpath);
                }
            }
        } catch (Exception e) {
            throw new MiniSpringException(0, LogEnum.spring.getName("exception"), "doScannerStaticResources", e.getMessage(), e.getStackTrace());
        }
    }

    /**
     * 静态资源读取方式加载
     */
    void loadStaticResourcesSuffixs(String sreamstr, String bufferstr) throws MiniSpringException {
        try {
            //加载字符流读取文件后缀
            Map<String, Object> FileStreamsuffixs = new HashMap<>(1);
            String[] filestreamsuffixs = sreamstr.split("\\|");
            for (String string : filestreamsuffixs) {
                FileStreamsuffixs.put(string, string);
            }
            GlobalParam.setFileStreamsuffixs(FileStreamsuffixs);
            new MiniSpringLog().printConsoleBlank(ScannerService.class, LogEnum.spring.getName("info"), " --loadStaticResourcesStreamSuffix-- ", JSONArray.fromObject(filestreamsuffixs));
            //加载字节流读取文件后缀
            Map<String, Object> Filebuffersuffixs = new HashMap<>(1);
            String[] filebuffersuffixs = bufferstr.split("\\|");
            for (String string : filebuffersuffixs) {
                Filebuffersuffixs.put(string, string);
            }
            GlobalParam.setFilebuffersuffixs(Filebuffersuffixs);
            new MiniSpringLog().printConsoleBlank(ScannerService.class, LogEnum.spring.getName("info"), " --loadStaticResourcesBufferSuffixs-- ", JSONArray.fromObject(filebuffersuffixs));
        } catch (Exception e) {
            throw new MiniSpringException(0, LogEnum.spring.getName("exception"), "loadStaticResourcesSuffixs", e.getMessage(), e.getStackTrace());
        }
    }
}
