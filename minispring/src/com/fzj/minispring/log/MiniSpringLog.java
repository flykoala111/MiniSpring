package com.fzj.minispring.log;

import com.fzj.minispring.common.MiniSpringDateTool;
import com.fzj.minispring.spring.GlobalParam;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

/*
 * @Author fzj
 * @Description //系统日志（公有）
 * @Date  13:58
 * @Param
 * @return
 **/
public class MiniSpringLog implements MiniSpringLogInterface {
    private static final String loglocationkey = "minispring.log.location";//日志文件位置的key
    private static final String logname = "minispring.log.filename";//日志文件名称

    private static BufferedWriter bw = null;//BufferedWriter对象
    private static Date lastDate = null;//上一次日志的时间

    @Override
    public void printException(String headname, Object... objects) {
        System.err.println("MinispringException:" + headname);
        for (Object object : objects) {
            System.err.println("        " + object);
        }
    }

    @Override
    public void printConsole(Class cla, String headname, Object... objects) {
        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("[").append(MiniSpringDateTool.yyyyMMddhhmmss()).append(" ")
                .append(headname).append(" ")
                .append(cla.getName()).append("]");
        for (Object object : objects) {
            stringBuffer.append(object);
        }
        System.err.println(stringBuffer.toString());
        writeLog(stringBuffer.toString());
    }

    @Override
    public void printConsoleBlank(Class cla, String headname, Object... objects) {
        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("[").append(MiniSpringDateTool.yyyyMMddhhmmss()).append(" ")
                .append(headname).append(" ")
                .append(cla.getName()).append("]");
        for (Object object : objects) {
            stringBuffer.append(object);
        }
        System.out.println(stringBuffer.toString());
        writeLog(stringBuffer.toString());
    }

    @Override
    public void printConsoleNontime(String headname, Object... objects) {
        StringBuffer stringBuffer = new StringBuffer(headname);
        stringBuffer.append("");
        for (Object object : objects) {
            stringBuffer.append(object);
        }
        System.err.println(stringBuffer.toString());
        writeLog(stringBuffer.toString());
    }

    @Override
    public void printConsoleNontimeBlank(String headname, Object... objects) {
        StringBuffer stringBuffer = new StringBuffer(headname);
        stringBuffer.append("");
        for (Object object : objects) {
            stringBuffer.append(object);
        }
        System.out.println(stringBuffer.toString());
        writeLog(stringBuffer.toString());
    }

    /**
     * 获取bufferedwriter对象
     *
     * @return
     */


    private void getBw() {
        if (lastDate != null) {
            if (!MiniSpringDateTool.yyyyMMddOnlynum(lastDate).equals(MiniSpringDateTool.yyyyMMddOnlynum(new Date()))) {
                //第二天，重新获取bw对象
                try {
                    bw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bw = null;
            }
        }
        //单例模式获得BufferedWriter对象
        if (bw == null) {
            String loglocation = GlobalParam.getProperties(loglocationkey) != null ? GlobalParam.getProperties(loglocationkey) : "";
            if ("".equals(loglocation)) {
                return;
            }
            File dir = new File(loglocation);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Date d = new Date();
            String path = loglocation + File.separator + GlobalParam.getProperties(logname) + MiniSpringDateTool.yyyyMMddOnlynum(d) + ".txt";
            lastDate = d;//保存当前日期
            File f = new File(path);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                bw = new BufferedWriter(new FileWriter(f, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写日志（追加）
     *
     * @param msg
     */
    private void writeLog(String msg) {
        getBw();
        if (bw != null) {
            try {
                bw.append(msg);
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
