package com.fzj.minispring.annotions;

import java.lang.annotation.*;

/**
 * 业务层（公有）
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anno_fzjService {
    String value() default "";
}
