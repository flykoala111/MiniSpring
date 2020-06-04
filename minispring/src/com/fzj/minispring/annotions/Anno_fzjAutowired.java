package com.fzj.minispring.annotions;

import java.lang.annotation.*;

/**
 * 依赖注入（公有）
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anno_fzjAutowired {
    String value() default "";
}