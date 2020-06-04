package com.fzj.minispring.annotions;

/**
 * 返回体（公有）
 **/

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anno_fzjResponseBody {
    boolean required() default true;
}