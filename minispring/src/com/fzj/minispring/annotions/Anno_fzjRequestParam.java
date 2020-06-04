package com.fzj.minispring.annotions;

import java.lang.annotation.*;

/**
 * 接受参数（公有）
 **/
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anno_fzjRequestParam {
    String value() default "";
}

