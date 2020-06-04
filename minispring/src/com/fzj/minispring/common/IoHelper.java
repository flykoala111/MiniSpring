package com.fzj.minispring.common;

import java.io.*;

/**
 * io工具类（公有）
 **/
public class IoHelper {
    /**
     * 字节流读取
     *
     * @param outputStream
     * @param filepath
     * @throws Exception
     */
    public static void outPrint(OutputStream outputStream, String filepath) throws IOException {
        outPrint(new File(filepath), outputStream);
    }

    /**
     * 字节流读取--重载
     *
     * @param outputStream
     * @param file
     * @throws Exception
     */
    public static void outPrint(OutputStream outputStream, File file) throws IOException {
        outPrint(file, outputStream);
    }

    private static void outPrint(File file, OutputStream outputStream) throws IOException {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int offset = 0;
            while ((offset = inputStream.read(bytes, 0, bytes.length)) != -1) {
                outputStream.write(bytes, 0, offset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    /**
     * 字符流读取文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static Object readFileAsStringbuffer(File file) throws IOException {
        return readFileAsStringbufferPublic(file);
    }

    /**
     * 字符流读取文件--重载
     *
     * @param filepath
     * @return
     * @throws Exception
     */
    public static Object readFileAsStringbuffer(String filepath) throws IOException {
        return readFileAsStringbufferPublic(new File(filepath));
    }

    private static Object readFileAsStringbufferPublic(File file) throws IOException {
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(file), "utf-8");
            br = new BufferedReader(inputStreamReader);
            String ss = "";
            while ((ss = br.readLine()) != null) {
                stringBuilder.append(ss).append("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (br != null) {
                br.close();
            }
        }
        return stringBuilder;
    }
}
