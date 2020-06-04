package com.fzj.minispring.minienum;

/**
 * 日志枚举（公有）
 */
public enum LogEnum {
    spring() {
        @Override
        public String getName(String msg) {
            String ms = "";
            switch (msg) {
                case "info":
                    ms = "MiniSpring-InFo";
                    break;
                case "exception":
                    ms = "MiniSpring-Exception";
                    break;
                case "debug":
                    ms = "MiniSpring-Debug";
                    break;
            }
            return ms;
        }
    },
    springjdbc() {
        @Override
        public String getName(String msg) {
            String ms = "";
            switch (msg) {
                case "info":
                    ms = "MiniSpringJdbc-InFo";
                    break;
                case "exception":
                    ms = "MiniSpringJdbc-Exception";
                    break;
                case "debug":
                    ms = "MiniSpringJdbc-Debug";
                    break;
            }
            return ms;
        }
    }, springmvc() {
        @Override
        public String getName(String msg) {
            String ms = "";
            switch (msg) {
                case "info":
                    ms = "MiniSpringMvc-InFo";
                    break;
                case "exception":
                    ms = "MiniSpringMvc-Exception";
                    break;
                case "debug":
                    ms = "MiniSpringMvc-Debug";
                    break;
            }
            return ms;
        }
    }, inteceptor() {
        @Override
        public String getName(String msg) {
            String ms = "";
            switch (msg) {
                case "info":
                    ms = "MiniSpringInteceptor-InFo";
                    break;
                case "exception":
                    ms = "MiniSpringInteceptor-Exception";
                    break;
                case "debug":
                    ms = "MiniSpringInteceptor-Debug";
                    break;
            }
            return ms;
        }
    },aop() {
        @Override
        public String getName(String msg) {
            String ms = "";
            switch (msg) {
                case "info":
                    ms = "MiniSpringAop-InFo";
                    break;
                case "exception":
                    ms = "MiniSpringAop-Exception";
                    break;
                case "debug":
                    ms = "MiniSpringAop-Debug";
                    break;
            }
            return ms;
        }
    };

    /**
     * 获取信息
     *
     * @param msg
     * @return
     */
    public abstract String getName(String msg);
}
