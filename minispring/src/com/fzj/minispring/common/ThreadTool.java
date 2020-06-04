package com.fzj.minispring.common;

/**
 *线程工具（公有）
 **/
public class ThreadTool {
    /**
     * 线程睡眠
     *
     * @param times
     */
    public synchronized static void threadSleep(long times) {
        try {
            Thread.sleep(times);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
