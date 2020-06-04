package com.fzj.minispring.springmvc;


import com.fzj.minispring.common.IoHelper;
import com.fzj.minispring.spring.GlobalParam;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * 静态资源解析(受保护)
 **/
class ModelAndViewParserFileImpl extends ModelAndViewParserImpl implements ModelAndViewParserFile {
    private MAndV mAndV;

    ModelAndViewParserFileImpl(MAndV mAndV) {
        this.mAndV = mAndV;
    }

    @Override
    public File getFile() {
        return new File(GlobalParam.getStaticResources().get(this.mAndV.getPath()));
    }

    @Override
    public Object getReturn() throws IOException, InvocationTargetException, IllegalAccessException {
        return super.parseView();
    }

    /**
     * 文件流输出
     *
     * @param outputStream
     * @throws Exception
     */
    @Override
    public void getReturn(OutputStream outputStream) throws IOException {
        File file = new File(String.valueOf(GlobalParam.getStaticResources().get(this.mAndV.getPath())));
        IoHelper.outPrint(outputStream, file);
    }
}
