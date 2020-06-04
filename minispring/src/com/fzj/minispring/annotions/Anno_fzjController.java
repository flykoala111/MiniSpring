package com.fzj.minispring.annotions;

/**
 * 控制器（公有）
 **/

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anno_fzjController {
    String value() default "";
}
