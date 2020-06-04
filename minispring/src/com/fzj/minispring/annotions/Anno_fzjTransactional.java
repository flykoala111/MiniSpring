package com.fzj.minispring.annotions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事务(针对于对数据库的操作)（公有）
 */
//作用于方法或者类
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Anno_fzjTransactional {
    String value() default "";
}
