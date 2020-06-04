package com.fzj.minispring.annotions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可适配注解（凡是被此注解的类都会加入到ioc容器里）（公有）
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Anno_fzjFlexibility {
    String value() default "";
}
