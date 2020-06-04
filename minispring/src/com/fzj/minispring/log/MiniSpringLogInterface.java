package com.fzj.minispring.log;

/**
 * @Author fzj
 * @Description //日志接口（受保护）
 * @Date 13:39
 * @Param
 * @return
 **/
interface MiniSpringLogInterface {
    /***
     *打印红色日志，带时间
     */
    public void printConsole(Class cla, String headname, Object... objects);

    /***
     *打印黑色日志，带时间
     */
    public void printConsoleBlank(Class cla, String headname, Object... objects);

    /***
     *打印红色日志，不带时间
     */
    public void printConsoleNontime(String headname, Object... objects);

    /***
     *打印黑色日志，不带时间
     */
    public void printConsoleNontimeBlank(String headname, Object... objects);

    /**
     * 打印手动异常输出
     */
    public void printException(String headname, Object... objects);
}
