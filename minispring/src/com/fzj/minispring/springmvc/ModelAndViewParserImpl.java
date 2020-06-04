package com.fzj.minispring.springmvc;

import com.fzj.minispring.common.IoHelper;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * 视图解析实现（受保护）
 **/
abstract class ModelAndViewParserImpl implements ModelAndViewParser {
    /**
     * 获取要解析的文件
     *
     * @return
     * @throws Exception
     */
    abstract File getFile() throws InvocationTargetException, IllegalAccessException;

    @Override
    public Object getReturn() throws Exception {
        //不实现，子类会重写
        return null;
    }

    @Override
    public Object parseView() throws InvocationTargetException, IOException, IllegalAccessException {
        //处理文件流编码
        return IoHelper.readFileAsStringbuffer(getFile());
    }
}
