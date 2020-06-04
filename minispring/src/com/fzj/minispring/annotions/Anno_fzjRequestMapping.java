package com.fzj.minispring.annotions;

/**
 * 请求映射（公有）
 **/

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anno_fzjRequestMapping {
    String value() default "";
}