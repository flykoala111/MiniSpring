package com.fzj.minispring.annotions;

import java.lang.annotation.*;

/**
 * mapper接口（公有）
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anno_fzjMapper {
    String value() default "";
}